package com.oriaxx77.micoserviceplay.order.web

import com.oriaxx77.micoserviceplay.order.app.OrderSaga
import com.oriaxx77.micoserviceplay.order.domain.OrderSagaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderSagaController(private val orderSagaRepository: OrderSagaRepository)
{
    @GetMapping("/ordersagas")
    fun getAll(): List<OrderSaga> = orderSagaRepository.getAll()

}
