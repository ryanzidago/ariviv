package com.ryanzidago.ariviv.data

import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_models.User
import java.time.LocalDateTime

var users = mutableListOf<User>(
    User("Jean", "jean@email.fr"),
    User("Günther", "guenther@email.de"),
    User("Julie", "julie@email.fr")
)

var domainEvents = mutableListOf<DomainEvent>()
val state = HashMap<String, LocalDateTime>()

fun defaultUsers(): MutableList<User> {
    return mutableListOf<User>(
        User("Jean", "jean@email.fr"),
        User("Günther", "guenther@email.de"),
        User("Julie", "julie@email.fr")
    )
}

fun defaultDomainEvents(): MutableList<DomainEvent> {
    return mutableListOf<DomainEvent>()
}