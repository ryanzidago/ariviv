package com.ryanzidago.ariviv.application_services

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.data.state
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import com.ryanzidago.ariviv.repositories.UserRepository
import java.time.LocalDateTime

class RegisterUserService {
    fun perform(name: String, email: String): User {
        val user = createUser(name, email)
        createUserRegisteredDomainEvent(user)
        createUserEnrolledInExerciseProgram(user)

        for (domainEvent in domainEvents) {
            println("${domainEvent.type}::${domainEvent.payload}")
        }

        return user
    }

    private fun createUser(name: String, email: String): User {
        val user = User(name, email)
        UserRepository().createUser(user)
        return user
    }

    private fun createUserRegisteredDomainEvent(user: User) {
        val payload = HashMap<Any, Any>()
        payload["name"] = user.name
        payload["email"] = user.email
        payload["id"] = user.id

        val userRegisteredDomainEvent =  DomainEvent(DomainEventType.UserRegistered, payload)
        DomainEventRepository().appendDomainEvent(userRegisteredDomainEvent)
    }

    private fun createUserEnrolledInExerciseProgram(user: User) {
        val payload = HashMap<Any, Any>()
        payload["name"] = user.name
        payload["email"] = user.email
        payload["id"] = user.id

        val userEnrolledInExerciseRoutine = DomainEvent(DomainEventType.UserEnrolledInExerciseRoutine, payload)
        DomainEventRepository().appendDomainEvent(userEnrolledInExerciseRoutine)
    }
}