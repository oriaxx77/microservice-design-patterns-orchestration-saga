package com.oriaxx77.micoserviceplay.order.domain

import com.oriaxx77.kotlin.extensions.isSome
import com.oriaxx77.micoserviceplay.order.app.OrderSaga
import com.oriaxx77.microserviceplay.saga.Identifiable
import com.oriaxx77.microserviceplay.saga.Order
import java.util.UUID

class OrderRepository: Repository<Order>()

class OrderSagaRepository: Repository<OrderSaga>()

/**
 * Unsafe nn memory repository. Not thread safe!!!!
 */
open class Repository<E> where E: Identifiable {
    private var entities = mutableListOf<E>()

    fun add(entity: E) = entities.add(entity)

    fun getAll() = entities // TODO: make it immutable

    fun getById(id: UUID) = entities.filter { it.id == id }.firstOrNull()

    fun save(entity: E) {
        val oldEntity = getById(entity.id)
        if (oldEntity.isSome()) {
            entities.remove(oldEntity)
        }
        add(entity)
    }
}


