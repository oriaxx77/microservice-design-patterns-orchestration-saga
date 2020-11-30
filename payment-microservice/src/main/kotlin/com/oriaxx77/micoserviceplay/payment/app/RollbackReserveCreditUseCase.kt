package com.oriaxx77.micoserviceplay.payment.app


import com.oriaxx77.micoserviceplay.payment.domain.MessageGateway
import com.oriaxx77.micoserviceplay.payment.domain.PaymentEventCode
import com.oriaxx77.micoserviceplay.payment.domain.RollbackReserveCreditRequest


class RollbackReserveCreditUseCase(private val messageGateway: MessageGateway)
{
    //TODO: use either
    //TODO: use AOP for logging
    fun exec(request: RollbackReserveCreditRequest)
    {
        print("RollbackReserveCreditCommandRequest arrived $request")
        messageGateway.publish(request.replyChannel,
                       request.createResponse(PaymentEventCode.CreditReservedRollbacked.toString()))
    }

}