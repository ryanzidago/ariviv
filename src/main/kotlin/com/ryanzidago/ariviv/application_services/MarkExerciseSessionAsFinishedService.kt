package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.ariviv.repositories.UserRepository
import com.ryanzidago.getLogger
import io.ktor.features.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

class MarkExerciseSessionAsFinishedService {
    private val logger = getLogger()

    fun perform(id: UUID) {
        val user = UserRepository().getUserById(id)
        if (user != null) {
            val finishedAt = updateExerciseFinishedAtForUser(user)
            createExerciseSessionMarkedAsFinishedDomainEvent(user, finishedAt)
        } else {
            val errorMessage = "No user with id $id could be found"
            logger.error(errorMessage)
            throw NotFoundException(errorMessage)
        }
    }

    private fun updateExerciseFinishedAtForUser(user: User): LocalDateTime {
        val finishedAt = LocalDateTime.now()
        state[user.id] = finishedAt
        return finishedAt
    }

    private fun createExerciseSessionMarkedAsFinishedDomainEvent(user: User, finishedAt: LocalDateTime) {
        val payload = HashMap<Any, Any>()
        payload["user"] = user.name
        payload["id"] = user.id
        payload["finishedAt"] = finishedAt
        val domainEvent = DomainEvent(DomainEventType.ExerciseSessionMarkedAsFinished, payload)
        DomainEventRepository().appendDomainEvent(domainEvent)
    }
}