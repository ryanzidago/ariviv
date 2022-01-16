package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.ariviv.repositories.UserRepository
import com.ryanzidago.getLogger
import com.ryanzidago.reminderToExerciseDelayInMS
import com.ryanzidago.timeToWaitBeforeCheckingIfReminderToExerciseShouldBeSentInMS
import io.ktor.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.Duration
import java.util.*
import kotlin.collections.HashMap

class SendReminderToExerciseService() {
    private val logger = getLogger()

    fun schedule() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                delay(timeToWaitBeforeCheckingIfReminderToExerciseShouldBeSentInMS)
                for ((id, lastExerciseSessionFinishedAt) in state) {
                    send(id, lastExerciseSessionFinishedAt)
                }
            }
        }
    }

    fun send(id: UUID, lastExerciseSessionFinishedAt: LocalDateTime) {
        if (Duration.between(lastExerciseSessionFinishedAt, LocalDateTime.now()) > Duration.ofMillis(reminderToExerciseDelayInMS)){
            val user = UserRepository().getUserById(id)
            if (user != null) {
                logger.info("\n\n [***] ${user.name}! Why haven't you completed your exercise yet?! [***] \n\n")
                appendDomainEvent(user)
            } else {
                val errorMessage = "User with id $id not found"
                logger.error(errorMessage)
                throw Exception(errorMessage)
            }
        }
    }

    private fun appendDomainEvent(user: User) {
        val payload = HashMap<Any, Any>()
        payload["name"] = user.name
        payload["email"] = user.email
        payload["id"] = user.id
        val domainEvent = DomainEvent(DomainEventType.ReminderToStartExerciseSessionSent, payload)

        DomainEventRepository().appendDomainEvent(domainEvent)
    }
}