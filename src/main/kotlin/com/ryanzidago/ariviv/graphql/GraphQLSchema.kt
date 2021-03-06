package com.ryanzidago.ariviv.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.ryanzidago.ariviv.application_services.MarkExerciseSessionAsFinishedService
import com.ryanzidago.ariviv.application_services.RegisterUserService
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.UserRepository
import java.util.*

fun SchemaBuilder.schemaValue() {
    type<User> {
        description = "A user object"
    }

    stringScalar<UUID> {
        serialize = UUID::toString
        deserialize = UUID::fromString
    }

    query("users") {
        description = "Retrieves all users"
        resolver { -> UserRepository().listUsers() ?: throw Exception("Could not list users")
        }
    }

    mutation("registerUser") {
        description = "Registers a user and automatically enrols them in an exercise routine"
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

    mutation("markExerciseSessionAsFinished") {
        description = "marks the Exercise Session of a user as finished"
        resolver { id: UUID ->
            MarkExerciseSessionAsFinishedService().perform(id)
            "User with id $id has finished a session full of exercises!"
        }.withArgs {
            arg<String> {
                name = "id"
                description = "The id of the user"
            }
        }
    }
}