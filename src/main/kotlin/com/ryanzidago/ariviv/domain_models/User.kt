package com.ryanzidago.ariviv.domain_models

import java.util.*

data class User(val name: String, val email: String, val id: UUID = UUID.randomUUID())