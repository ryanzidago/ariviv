package com.ryanzidago

import io.ktor.application.*
import com.apurebase.kgraphql.GraphQL

import com.ryanzidago.plugins.configureRouting
import com.ryanzidago.ariviv.graphql.schemaValue

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(testing: Boolean = false) {
    configureRouting()

    install(GraphQL) {
        playground = true
        schema {
            schemaValue()
        }
    }
}
