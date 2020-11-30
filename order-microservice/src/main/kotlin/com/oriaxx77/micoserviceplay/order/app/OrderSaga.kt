package com.oriaxx77.micoserviceplay.order.app

import com.oriaxx77.kotlin.extensions.flatMap
import com.oriaxx77.kotlin.extensions.orThrow
import com.oriaxx77.micoserviceplay.order.domain.OrderRepository
import com.oriaxx77.micoserviceplay.order.domain.PaymentService
import com.oriaxx77.micoserviceplay.order.domain.ShippingService
import com.oriaxx77.microserviceplay.saga.Identifiable
import com.oriaxx77.microserviceplay.saga.Order
import com.oriaxx77.microserviceplay.saga.OrderStatus
import java.util.*

class MissingOrderException(orderId: UUID?): Exception("Missing order. OrderId: $orderId")



class OrderSaga(private val paymentService: PaymentService,
                private val shippingService: ShippingService,
                private val orderRepository: OrderRepository,
                var events: MutableList<String> = mutableListOf(),
                private var orderId: UUID? = null
                )//TODO use a complex type for the finish
    : Identifiable()
{

    companion object {
        const val replyTopic = "OrderSaga"
    }


    fun start(order: Order){
        this.orderId = order.id //TODO: do we need it? We need a blockchain?
        orderRepository.add(order)
        paymentService.reserveCredit(id, replyTopic, order)
    }

    fun handleEvent(event: OrderSagaEvent) {
        events.add(event.toString())
        when(event){
            is CreditExceededEvent -> onCreditExceeded()
            is CreditReservedEvent -> onCreditReserved()
            is OrderShippedEvent -> onOrderShipped()
            is OrderUnshippableEvent -> onOrderUnshippable()
            is CreditReservedRollbackedEvent -> onCreditReservedRollbackedEvent()
            is UnknownEvent -> onUnknownEvent()
        }
    }


    private fun setNewStatus(newStatus: OrderStatus) {
        orderId
                .flatMap { orderRepository.getById(it) }
                .flatMap { orderRepository.save(it.copy(status = newStatus)) }
    }

    private fun getOrder()
        = orderId?.let { orderId ->  orderRepository.getById(orderId) }
            .orThrow { MissingOrderException(orderId = orderId) }



    private fun onCreditExceeded() {
        setNewStatus(OrderStatus.CREDIT_EXCEEDED)
        // TODO: order workflow failed :(
    }

    private fun onCreditReserved() {
        setNewStatus(OrderStatus.CREDIT_RESERVED)
        shippingService.shipOrder(id, replyTopic, getOrder())
    }


    private fun onCreditReservedRollbackedEvent() {
        // TODO: what to do?
    }

    private fun onOrderShipped() {
        setNewStatus(OrderStatus.SHIPPED)
        // TODO: order workflow completed :)
    }

    private fun onOrderUnshippable() {
        setNewStatus(OrderStatus.UNSHIPPABLE)
        paymentService.rollbackReserveCredit(id, replyTopic, getOrder())
    }

    private fun onUnknownEvent() {
        // TODO: what to do?
    }

}








