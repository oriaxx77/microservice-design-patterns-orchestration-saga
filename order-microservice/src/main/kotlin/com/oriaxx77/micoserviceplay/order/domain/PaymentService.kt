package com.oriaxx77.micoserviceplay.order.domain

import com.oriaxx77.microserviceplay.saga.Order
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.kafka.core.KafkaTemplate
import java.util.*



class PaymentService(kafkaTemplate: KafkaTemplate<String, String>)
    : Microservice(name = "PaymentMicroservice", kafkaTopicName = "payments", kafkaTemplate = kafkaTemplate){

    fun reserveCredit(sagaId: UUID, replyChannel: String, order: Order) =
        sendRequest(OrderSagaCommandRequest(sagaId = sagaId, replyChannel = replyChannel,
                        microservice = name, command = "reserveCredit", order = order))


    fun rollbackReserveCredit(sagaId: UUID, replyChannel: String, order: Order) =
        sendRequest(OrderSagaCommandRequest(sagaId = sagaId,
                                            replyChannel = replyChannel,
                                            microservice = name,
                                            command = "rollbackReserveCredit",
                                            order = order))

}
