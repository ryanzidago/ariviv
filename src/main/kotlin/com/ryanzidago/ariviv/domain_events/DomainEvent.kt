package com.ryanzidago.ariviv.domain_events

enum class DomainEventType {
    UserRegistered,
    UserEnrolledInExerciseRoutine,
    ExerciseSessionMarkedAsFinished,
}

data class DomainEvent(val type: DomainEventType, val payload: HashMap<Any, Any>)

