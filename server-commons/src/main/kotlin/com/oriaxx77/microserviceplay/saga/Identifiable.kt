package com.oriaxx77.microserviceplay.saga

import java.util.UUID

open class Identifiable(val id: UUID = UUID.randomUUID())