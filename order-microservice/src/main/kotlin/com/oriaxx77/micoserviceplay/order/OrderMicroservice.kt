package com.oriaxx77.micoserviceplay.order


import com.oriaxx77.micoserviceplay.order.app.NewOrderUseCase
import com.oriaxx77.micoserviceplay.order.app.OrderSagaOrchestrator
import com.oriaxx77.micoserviceplay.order.domain.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class OrderMicroservice
{
    @Bean fun orderRepository() = OrderRepository()

    @Bean fun orderSagaRepository() = OrderSagaRepository()

    @Bean fun orderSagaOrchestrator(paymentService: PaymentService, shippingService: ShippingService) =
            OrderSagaOrchestrator(paymentService, shippingService, orderSagaRepository(), orderRepository())

    @Bean fun createOrderUseCase(orderSagaOrchestrator: OrderSagaOrchestrator) =
            NewOrderUseCase(orderSagaOrchestrator)

    @Bean fun paymentService(kafkaTemplate: KafkaTemplate<String, String>) = PaymentService(kafkaTemplate)

    @Bean fun shippingService(kafkaTemplate: KafkaTemplate<String,String>) = ShippingService(kafkaTemplate)
}


fun main(args: Array<String>)
{
    SpringApplication.run(OrderMicroservice::class.java, *args)
}