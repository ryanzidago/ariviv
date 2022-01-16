package com.ryanzidago.ariviv.data

import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_models.User
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

val jeanUUID = UUID.fromString("a8c50e4c-cf54-4466-aac4-254cd089a8eb")
val guentherUUID = UUID.fromString("e285352b-b4f9-44d1-9f58-d77951573b18")
val julieUUID = UUID.fromString("331fe07b-73d1-4c87-9f1a-2fcdc5bc69d5")

var users = mutableListOf<User>(
    User("Jean", "jean@email.fr", jeanUUID),
    User("Günther", "guenther@email.de", guentherUUID),
    User("Julie", "julie@email.fr", julieUUID)
)

var domainEvents = mutableListOf<DomainEvent>()
val state = HashMap<UUID, LocalDateTime>()

fun defaultUsers(): MutableList<User> {
    return mutableListOf<User>(
        User("Jean", "jean@email.fr", jeanUUID),
        User("Günther", "guenther@email.de", guentherUUID),
        User("Julie", "julie@email.fr", julieUUID)
    )
}

fun defaultDomainEvents(): MutableList<DomainEvent> {
    return mutableListOf<DomainEvent>()
}