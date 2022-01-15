package com.ryanzidago.ariviv.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.ryanzidago.ariviv.repositories.UserRepository

fun SchemaBuilder.schemaValue() {
    query("users") {
        description = "Retrieves all users"
        resolver {
            -> UserRepository().listUsers() ?: throw Exception("Could not list users")
        }
    }
}