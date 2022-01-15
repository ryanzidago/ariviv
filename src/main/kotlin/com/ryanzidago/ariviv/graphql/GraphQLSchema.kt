package com.ryanzidago.ariviv.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.ryanzidago.ariviv.application_services.RegisterUserService
import com.ryanzidago.ariviv.repositories.UserRepository

fun SchemaBuilder.schemaValue() {
    query("users") {
        description = "Retrieves all users"
        resolver { -> UserRepository().listUsers() ?: throw Exception("Could not list users")
        }
    }

    mutation("registerUser") {
        description = "Registers a user"
        resolver { name: String, email: String  ->
          RegisterUserService().perform(name, email) ?: throw Exception("User could not be created")
        }.withArgs {
            arg<String> {
                name = "name"
                description = "The name of the user"
            }
            arg<String> {
                name = "email"
                description = "The email address of the user"
            }
        }
    }
}