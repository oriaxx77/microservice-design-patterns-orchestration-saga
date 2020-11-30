package com.oriaxx77.micoserviceplay.order.app

import com.oriaxx77.microserviceplay.saga.Order

class NewOrderUseCase ( private val orderSagaOrchestrator: OrderSagaOrchestrator) {
    fun exec() {
        orderSagaOrchestrator.startSaga(Order())
    }
}