package com.oriaxx77.micoserviceplay.order.web

import com.oriaxx77.micoserviceplay.order.app.NewOrderUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(private val newOrderUseCase: NewOrderUseCase)
{
    // TODO: this should be POST
    @GetMapping("/order")
    fun create(): String
    {
        newOrderUseCase.exec()
        return "Message sent to kafka" //TODO: remove this when REST implemented, maybe return with the SAGA
    }
}