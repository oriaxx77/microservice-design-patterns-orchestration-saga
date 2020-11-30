package com.oriaxx77.microserviceplay.saga

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.util.*

class OrderSagaCommandRequest(val sagaId: UUID,
                              val replyChannel: String,
                              val microservice: String,
                              val command: String,
                              val order: Order) {
    companion object {
        fun fromJsonString(json: String): OrderSagaCommandRequest?
                = try { Gson().fromJson(json, OrderSagaCommandRequest::class.java)}
        catch(ex: JsonSyntaxException){null}
    }

}
