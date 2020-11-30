package com.oriaxx77.micoserviceplay.order.app

import com.oriaxx77.microserviceplay.saga.OrderSagaCommandReply

sealed class OrderSagaEvent
data class CreditReservedEvent(val message: String): OrderSagaEvent()
data class CreditReservedRollbackedEvent(val message: String): OrderSagaEvent()
data class CreditExceededEvent(val message: String): OrderSagaEvent()
data class OrderShippedEvent(val message: String): OrderSagaEvent()
data class OrderUnshippableEvent(val message: String): OrderSagaEvent()
data class UnknownEvent(val event: String): OrderSagaEvent()

// TODO: make it object oriented
// TODO: use constants
// TODO: use microservices.command.message
fun OrderSagaCommandReply.orderEvent() =
        when (this.command) {
            "reserveCredit" -> {
                when (this.message) {
                    "CreditReserved" -> CreditReservedEvent(message)
                    "CreditExceeded" -> CreditExceededEvent(message)
                    else -> UnknownEvent("${command}.${message}")
                }
            }
            // TODO: handle message
            "rollbackReserveCredit" -> CreditReservedRollbackedEvent(message)
            "shipOrder" -> {
                when (this.message) {
                    "OrderShipped" -> OrderShippedEvent(message)
                    "OrderUnshippable" -> OrderUnshippableEvent(message)
                    else -> UnknownEvent("${command}.${message}")
                }
            }
            else -> UnknownEvent(command)
        }
