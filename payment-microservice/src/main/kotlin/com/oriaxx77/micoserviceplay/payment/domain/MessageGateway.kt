package com.oriaxx77.micoserviceplay.payment.domain

import com.google.gson.Gson
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandReply
import org.springframework.kafka.core.KafkaTemplate


class MessageGateway(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun publish(topic: String, replyMessage: OrderSagaCommandReply) {
        kafkaTemplate.send(topic, Gson().toJson(replyMessage))
    }
}