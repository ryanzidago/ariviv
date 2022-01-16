package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.reminderToExerciseDelayInMS
import io.ktor.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.Duration


class SendReminderToExerciseService() {
    fun schedule() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1_000)
                for ((userName, lastExerciseSessionFinishedAt) in state) {
                    send(userName, lastExerciseSessionFinishedAt)
                }
            }
        }
    }

    fun send(userName: String, lastExerciseSessionFinishedAt: LocalDateTime) {
        if (Duration.between(lastExerciseSessionFinishedAt, LocalDateTime.now()) > Duration.ofMillis(reminderToExerciseDelayInMS) ){
            println("${userName}! Why haven't you completed your exercise yet?!")
            appendDomainEvent(userName)
        }
    }

    private fun appendDomainEvent(userName: String) {
        val payload = HashMap<Any, Any>()
        payload["name"] = userName
        val domainEvent = DomainEvent(DomainEventType.ReminderToStartExerciseSessionSent, payload)

        DomainEventRepository().appendDomainEvent(domainEvent)
    }
}