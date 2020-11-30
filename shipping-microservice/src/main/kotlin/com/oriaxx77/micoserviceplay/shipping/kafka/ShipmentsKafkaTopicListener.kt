package com.oriaxx77.micoserviceplay.shipping.kafka

import com.oriaxx77.micoserviceplay.shipping.app.ShipOrderUseCase
import com.oriaxx77.micoserviceplay.shipping.domain.ShipOrderRequest
import com.oriaxx77.micoserviceplay.shipping.domain.UnknownCommandRequest
import com.oriaxx77.micoserviceplay.shipping.domain.shippingMicroserviceRequest
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener




@KafkaListener(topics = ["shipments"])
class ShipmentsKafkaTopicListener(private val shipOrderUseCase: ShipOrderUseCase) {

    @KafkaHandler
    fun processShippingCommands(commandJson: String)
    {
        OrderSagaCommandRequest
                .fromJsonString(commandJson)
                ?.shippingMicroserviceRequest().let {
                    when(it) {
                        is ShipOrderRequest -> shipOrderUseCase.exec(it)
                        is UnknownCommandRequest -> print("Unknown Payment command received: ${it}")
                    }
                }
    }
}