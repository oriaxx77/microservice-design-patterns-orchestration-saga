package com.oriaxx77.microserviceplay.saga

import com.google.gson.Gson
import java.util.*

class OrderSagaCommandReply(val sagaId: UUID,
                            val microservice: String,
                            val command: String,
                            val message: String)
{
    companion object {
        fun fromJsonString(jsonString: String) =
            try {Gson().fromJson(jsonString,OrderSagaCommandReply::class.java)}
            catch(e:Exception) { null }
    }
}
