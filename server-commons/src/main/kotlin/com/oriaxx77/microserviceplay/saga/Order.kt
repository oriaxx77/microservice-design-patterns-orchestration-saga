package com.oriaxx77.microserviceplay.saga

enum class OrderStatus {
    NEW, CREDIT_RESERVED, CREDIT_EXCEEDED, SHIPPED, UNSHIPPABLE
}

data class Order (val status: OrderStatus = OrderStatus.NEW) : Identifiable() {
    private fun newStatus(newStatus: OrderStatus): Order = copy(status = newStatus)
}