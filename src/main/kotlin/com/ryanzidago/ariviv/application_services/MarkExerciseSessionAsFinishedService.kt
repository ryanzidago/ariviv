package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.ariviv.repositories.UserRepository
import java.time.LocalDateTime

class MarkExerciseSessionAsFinishedService {
    fun perform(name: String) {
        val user = UserRepository().getUserByName(name)
        if (user != null) {
            val finishedAt = updateExerciseFinishedAtForUser(user)
            createExerciseSessionMarkedAsFinishedDomainEvent(user, finishedAt)
        }

        for (domainEvent in domainEvents) {
            println("${domainEvent.type}::${domainEvent.payload}")
        }
    }

    private fun updateExerciseFinishedAtForUser(user: User): LocalDateTime {
        val finishedAt = LocalDateTime.now()
        state[user.name] = finishedAt
        return finishedAt

    }

    private fun createExerciseSessionMarkedAsFinishedDomainEvent(user: User, finishedAt: LocalDateTime) {
        val payload = HashMap<Any, Any>()
        payload["user"] = user.name
        payload["finishedAt"] = finishedAt
        val domainEvent = DomainEvent(DomainEventType.ExerciseSessionMarkedAsFinished, payload)
        DomainEventRepository().appendDomainEvent(domainEvent)
    }



}