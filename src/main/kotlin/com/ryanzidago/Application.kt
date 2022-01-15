package com.ryanzidago

import io.ktor.application.*
import com.apurebase.kgraphql.GraphQL
import com.ryanzidago.ariviv.application_services.ExerciseReminderService
import com.ryanzidago.plugins.configureRouting
import com.ryanzidago.ariviv.graphql.schemaValue
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.features.DefaultHeaders

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    configureRouting()

    ExerciseReminderService().schedule()

    routing {
        static("/graphql/doc") {
            resources("/graphql/doc")
        }
    }

    install(GraphQL) {
        playground = true
        schema {
            schemaValue()
        }
    }
}
