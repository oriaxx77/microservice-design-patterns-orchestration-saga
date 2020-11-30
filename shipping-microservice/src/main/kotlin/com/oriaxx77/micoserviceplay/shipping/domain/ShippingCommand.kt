package com.oriaxx77.micoserviceplay.shipping.domain

import com.oriaxx77.microserviceplay.saga.OrderSagaCommandReply
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import java.util.*


fun OrderSagaCommandRequest.shippingMicroserviceRequest() =
        when( command ) {
            ShipOrderRequest.COMMAND -> ShipOrderRequest(sagaId = sagaId, replyChannel = replyChannel)
            else -> UnknownCommandRequest(sagaId = sagaId, command = command, replyChannel = replyChannel)
        }



sealed class ShippingMicroserviceRequest(val sagaId: UUID, val command: String, val replyChannel: String) {
    fun createResponse(message: String) = OrderSagaCommandReply(sagaId = sagaId,
            microservice = "PaymentMicroservice",
            command = command(),
            message = message)
    abstract fun command(): String
}



class ShipOrderRequest(sagaId: UUID, replyChannel: String)
    : ShippingMicroserviceRequest(sagaId = sagaId, command = COMMAND, replyChannel = replyChannel) {
    companion object {
        val COMMAND = "shipOrder"
    }
    override fun command() = COMMAND
}

class UnknownCommandRequest(sagaId: UUID, replyChannel: String, command: String)
    : ShippingMicroserviceRequest(sagaId = sagaId, command = command, replyChannel = replyChannel) {
    override fun command() = command
}




