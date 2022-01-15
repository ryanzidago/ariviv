package com.ryanzidago.ariviv.data

import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_models.User
import java.time.LocalDateTime

val users = mutableListOf<User>(
    User("Jean", "jean@email.fr"),
    User("Günther", "guenther@email.de"),
    User("Julie", "julie@email.fr")
)

val domainEvents = mutableListOf<DomainEvent>()
val state = HashMap<String, LocalDateTime>()