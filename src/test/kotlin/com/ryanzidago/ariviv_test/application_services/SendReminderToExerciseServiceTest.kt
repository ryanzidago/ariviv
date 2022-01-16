package com.ryanzidago.ariviv_test.application_services

import com.ryanzidago.ariviv.application_services.SendReminderToExerciseService
import com.ryanzidago.ariviv.data.defaultDomainEvents
import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.Test

class SendReminderToExerciseServiceTest {
    @Test
    fun sendFunctionDoesNotSendsANotificationToTheUserIfTheyHaveExerciseRecently() {
        val lastExerciseSessionFinishedAt = LocalDateTime.now()
        SendReminderToExerciseService().send("Jean", lastExerciseSessionFinishedAt)
        assert(domainEvents.isEmpty())
    }

    @Test
    fun sendFunctionSendsANotificationToTheUserIfTheyHaveNotExerciseForACertainTime() {
        val expectedPayload = HashMap<Any, Any>()
        expectedPayload["name"] = "Jean"
        val expectedDomainEvent = DomainEvent(DomainEventType.ReminderToStartExerciseSessionSent, expectedPayload)
        val lastExerciseSessionFinishedAt = LocalDateTime.of(2020, Month.JANUARY, 1, 1, 0,0)
        SendReminderToExerciseService().send("Jean", lastExerciseSessionFinishedAt)

        assert(domainEvents.contains(expectedDomainEvent))
        domainEvents = defaultDomainEvents()
    }


}