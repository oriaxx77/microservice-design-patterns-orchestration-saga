package com.oriaxx77.micoserviceplay.payment

import com.oriaxx77.micoserviceplay.payment.app.ReserveCreditUseCase
import com.oriaxx77.micoserviceplay.payment.app.RollbackReserveCreditUseCase
import com.oriaxx77.micoserviceplay.payment.domain.MessageGateway
import com.oriaxx77.micoserviceplay.payment.kafkaendpoints.PaymentsKafkaListener
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
private class PaymentMicroservice
{

    @Bean
    fun paymentsKafkaListener(reserveCreditUseCase: ReserveCreditUseCase,
                              rollbackReserveCreditUseCse: RollbackReserveCreditUseCase)
            = PaymentsKafkaListener(reserveCreditUseCase, rollbackReserveCreditUseCse)

    @Bean
    fun messageGateway(kafkaTemplate: KafkaTemplate<String, String>)
            = MessageGateway(kafkaTemplate)

    @Bean
    fun reserveCreditUseCase(messageGateway: MessageGateway)
            = ReserveCreditUseCase(messageGateway)

    @Bean
    fun rollbackReserveCreditUseCase(messageGateway: MessageGateway)
            = RollbackReserveCreditUseCase(messageGateway)
}


fun main(args: Array<String>)
{
    SpringApplication.run(PaymentMicroservice::class.java, *args)
}