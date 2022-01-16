package com.ryanzidago.ariviv_test.application_services

import com.ryanzidago.ariviv.application_services.SendReminderToExerciseService
import com.ryanzidago.ariviv.data.defaultDomainEvents
import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.data.jeanUUID
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.Test

class SendReminderToExerciseServiceTest {
    @Test
    fun sendFunctionDoesNotSendsANotificationToTheUserIfTheyHaveExerciseRecently() {
        val lastExerciseSessionFinishedAt = LocalDateTime.now()
        SendReminderToExerciseService().send(jeanUUID, lastExerciseSessionFinishedAt)
        assert(domainEvents.isEmpty())
    }

    @Test
    fun sendFunctionSendsANotificationToTheUserIfTheyHaveNotExerciseForACertainTime() {
        val payload = HashMap<Any, Any>()
        payload["name"] = "Jean"
        payload["email"] = "jean@email.fr"
        payload["id"] = jeanUUID

        val expectedDomainEvent = DomainEvent(DomainEventType.ReminderToStartExerciseSessionSent, payload)
        val lastExerciseSessionFinishedAt = LocalDateTime.of(2020, Month.JANUARY, 1, 1, 0,0)

        SendReminderToExerciseService().send(jeanUUID, lastExerciseSessionFinishedAt)
        assert(domainEvents.contains(expectedDomainEvent))

        domainEvents = defaultDomainEvents()
    }


}