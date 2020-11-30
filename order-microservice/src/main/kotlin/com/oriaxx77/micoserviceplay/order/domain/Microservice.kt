package com.oriaxx77.micoserviceplay.order.domain

import com.google.gson.Gson
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.kafka.core.KafkaTemplate

open class Microservice(val name: String, private val kafkaTopicName: String,
                   private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun sendRequest(request: OrderSagaCommandRequest) {
        kafkaTemplate.send(kafkaTopicName, Gson().toJson(request))
    }
}