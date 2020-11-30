package com.oriaxx77.micoserviceplay.order.app

import com.oriaxx77.micoserviceplay.order.domain.OrderRepository
import com.oriaxx77.micoserviceplay.order.domain.OrderSagaRepository
import com.oriaxx77.micoserviceplay.order.domain.PaymentService
import com.oriaxx77.micoserviceplay.order.domain.ShippingService
import com.oriaxx77.microserviceplay.saga.Order
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandReply
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener

@KafkaListener(topics=[OrderSaga.replyTopic])
class OrderSagaOrchestrator(private val paymentService: PaymentService,
                            private val shippingService: ShippingService,
                            private val orderSagaRepository: OrderSagaRepository,
                            private val orderRepository: OrderRepository){

    fun startSaga(order: Order) {
        val orderSaga = OrderSaga(paymentService, shippingService, orderRepository)
        orderSaga.start(order)
        orderSagaRepository.add(orderSaga)
    }

    @KafkaHandler
    fun handleSagaEvent(eventJson: String) {
        OrderSagaCommandReply
                .fromJsonString(eventJson)
                ?.let {
                    val orderSaga = orderSagaRepository.getById(it.sagaId)
                    orderSaga?.handleEvent(it.orderEvent())
                }

    }
}
