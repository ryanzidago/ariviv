package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.ariviv.repositories.UserRepository
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
                println("${user.name}! Why haven't you completed your exercise yet?!")
                appendDomainEvent(user)
            } else {
                throw Exception("User with id $id not found")
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