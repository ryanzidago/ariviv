package com.ryanzidago

import io.ktor.application.*
import com.apurebase.kgraphql.GraphQL
import com.ryanzidago.ariviv.application_services.SendReminderToExerciseService
import com.ryanzidago.plugins.configureRouting
import com.ryanzidago.ariviv.graphql.schemaValue
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.features.DefaultHeaders
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val timeToWaitBeforeCheckingIfReminderToExerciseShouldBeSentInMS: Long =
    System.getenv("TIME_TO_WAIT_BEFORE_CHECKING_IF_REMINDER_TO_EXERCISE_SHOULD_BE_SENT_IN_MS")?.toLong() ?: (1_000 * 5) // 5 seconds in milliseconds

val reminderToExerciseDelayInMS: Long =
    System.getenv("REMINDER_TO_EXERCISE_DELAY_IN_MS")?.toLong() ?: (((1_000 * 60) * 60) * 24) // 1 day in milliseconds

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(testing: Boolean = false) {
    val logger = getLogger()

    logger.info("timeToWaitBeforeCheckingIfReminderToExerciseShouldBeSentInMS=$timeToWaitBeforeCheckingIfReminderToExerciseShouldBeSentInMS")
    logger.info("reminderToExerciseDelayInMS=$reminderToExerciseDelayInMS")

    install(DefaultHeaders)
    configureRouting()

    SendReminderToExerciseService().schedule()

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

inline fun <reified T> T.getLogger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}
