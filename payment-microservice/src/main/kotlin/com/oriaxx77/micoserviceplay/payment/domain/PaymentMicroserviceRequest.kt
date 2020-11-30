package com.oriaxx77.micoserviceplay.payment.domain

import com.oriaxx77.microserviceplay.saga.OrderSagaCommandReply
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import java.util.*


abstract sealed class PaymentMicroserviceRequest(val sagaId: UUID, val command: String, val replyChannel: String)
{
    fun createResponse(message: String) = OrderSagaCommandReply(sagaId = sagaId,
            microservice = "PaymentMicroservice",
            command = command,
            message = message)

    abstract fun name(): String
}

class ReserveCreditRequest(sagaId: UUID, replyChannel: String)
    : PaymentMicroserviceRequest(sagaId, NAME, replyChannel)
{
    companion object {
        val NAME = "reserveCredit"
    }

    override fun name() = NAME
}


class RollbackReserveCreditRequest(sagaId: UUID, replyChannel: String)
    : PaymentMicroserviceRequest(sagaId, NAME, replyChannel)
{
    companion object {
        val NAME = "rollbackReserveCredit"
    }

    override fun name() = NAME
}


class UnknownRequest(sagaId: UUID, replyChannel: String, command: String)
    : PaymentMicroserviceRequest(sagaId, command, replyChannel)
{
    override fun name() = "unknownRequest"
}

fun OrderSagaCommandRequest.paymentMicroserviceRequest() =
        when (command) {
            ReserveCreditRequest.NAME -> ReserveCreditRequest(sagaId, replyChannel)
            RollbackReserveCreditRequest.NAME -> RollbackReserveCreditRequest(sagaId, replyChannel)
            else -> UnknownRequest(sagaId, command, replyChannel)
        }






