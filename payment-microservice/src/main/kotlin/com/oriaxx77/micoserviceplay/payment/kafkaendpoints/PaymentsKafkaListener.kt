package com.oriaxx77.micoserviceplay.payment.kafkaendpoints

import com.oriaxx77.micoserviceplay.payment.app.ReserveCreditUseCase
import com.oriaxx77.micoserviceplay.payment.app.RollbackReserveCreditUseCase
import com.oriaxx77.micoserviceplay.payment.domain.ReserveCreditRequest
import com.oriaxx77.micoserviceplay.payment.domain.RollbackReserveCreditRequest
import com.oriaxx77.micoserviceplay.payment.domain.UnknownRequest
import com.oriaxx77.micoserviceplay.payment.domain.paymentMicroserviceRequest
import com.oriaxx77.microserviceplay.saga.OrderSagaCommandRequest
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener


@KafkaListener(topics = ["payments"])
class PaymentsKafkaListener(private val reserveCreditUseCase: ReserveCreditUseCase,
                            private val rollbackReserveCreditUseCse: RollbackReserveCreditUseCase)
{
    @KafkaHandler
    fun processPaymentCommands(commandJson: String)
    {
        OrderSagaCommandRequest
                .fromJsonString(commandJson)
                ?.paymentMicroserviceRequest().let {
                    when(it) {
                        is ReserveCreditRequest -> reserveCreditUseCase.exec(it)
                        is RollbackReserveCreditRequest -> rollbackReserveCreditUseCse.exec(it)
                        is UnknownRequest -> print("Unknown Payment command received: $it")
                    }
                }
    }
}