package com.oriaxx77.micoserviceplay.shipping

import com.oriaxx77.micoserviceplay.shipping.app.ShipOrderUseCase
import com.oriaxx77.micoserviceplay.shipping.domain.*
import com.oriaxx77.micoserviceplay.shipping.kafka.ShipmentsKafkaTopicListener
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class ShippingMicroservice
{

    @Bean
    fun shipmentsKafkaTopicListener(shipOrderUseCase: ShipOrderUseCase)
        = ShipmentsKafkaTopicListener(shipOrderUseCase)


    @Bean
    fun shipOrderUseCase(messageGateway: MessageGateway)
            = ShipOrderUseCase(messageGateway = messageGateway)


    @Bean
    fun messageGateway(kafkaTemplate: KafkaTemplate<String, String>)
            = MessageGateway(kafkaTemplate = kafkaTemplate)

}


fun main(args: Array<String>)
{
    SpringApplication.run(ShippingMicroservice::class.java, *args)
}