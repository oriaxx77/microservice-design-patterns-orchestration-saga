package com.oriaxx77.micoserviceplay.shipping.app


import com.oriaxx77.micoserviceplay.shipping.domain.MessageGateway
import com.oriaxx77.micoserviceplay.shipping.domain.ShippingEventCode
import com.oriaxx77.micoserviceplay.shipping.domain.ShipOrderRequest
import java.util.UUID
import kotlin.random.Random

typealias TransactionId = UUID
typealias OrderId = UUID

class ShipOrderUseCase(private val messageGateway: MessageGateway)
{
    //TODO: use either
    //TODO: use AOP for logging
    fun exec(request: ShipOrderRequest)
    {
        messageGateway.publish(request.replyChannel, request.createResponse(getMessage()))
    }

    private fun getMessage() =
            if (shouldReject()) ShippingEventCode.OrderShipped.toString()
            else ShippingEventCode.OrderUnshippable.toString()

    private fun shouldReject() = Random.nextBoolean()
}