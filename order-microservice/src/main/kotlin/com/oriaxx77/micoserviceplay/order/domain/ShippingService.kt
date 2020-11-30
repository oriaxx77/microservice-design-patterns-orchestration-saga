package com.oriaxx77.micoserviceplay.order.domain

import com.oriaxx77.microserviceplay.saga.Order
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.kafka.core.KafkaTemplate
import java.util.*


class ShippingService(kafkaTemplate: KafkaTemplate<String, String>)
    : Microservice(name = "ShippingMicroservice", kafkaTopicName = "shipments", kafkaTemplate = kafkaTemplate){

    fun shipOrder(sagaId: UUID, replyChannel: String, order: Order) {
        sendRequest(OrderSagaCommandRequest(sagaId = sagaId, replyChannel = replyChannel,
                                            microservice = name, command = "shipOrder", order = order))
    }

    fun rollbackShipOrder(sagaId: UUID, replyChannel: String, order: Order) {
        sendRequest(OrderSagaCommandRequest(sagaId = sagaId, replyChannel = replyChannel,
                                            microservice = name, command = "rollbackShipOrder", order = order ))
    }

}


