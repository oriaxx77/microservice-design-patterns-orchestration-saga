package com.oriaxx77.micoserviceplay.payment.app


import com.oriaxx77.micoserviceplay.payment.domain.MessageGateway
import com.oriaxx77.micoserviceplay.payment.domain.PaymentEventCode
import com.oriaxx77.micoserviceplay.payment.domain.ReserveCreditRequest
import kotlin.random.Random


class ReserveCreditUseCase(private val messageGateway: MessageGateway)
{
    //TODO: use either
    //TODO: use AOP for logging
    fun exec(request: ReserveCreditRequest)
    {
        messageGateway.publish(request.replyChannel, request.createResponse(getMessage()))
    }

    private fun getMessage() =
            if (shouldReject()) PaymentEventCode.CreditExceeded.toString()
            else PaymentEventCode.CreditReserved.toString()

    private fun shouldReject() = Random.nextBoolean()
}