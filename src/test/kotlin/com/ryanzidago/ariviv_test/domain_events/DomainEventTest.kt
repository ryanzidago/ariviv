package com.ryanzidago.ariviv_test.domain_events

import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import org.junit.Test
import kotlin.test.assertEquals

class DomainEventTest {
    @Test
    fun canCreateAnInstanceOfDomainEvent() {
        val payload = HashMap<Any, Any>()
        val domainEvent = DomainEvent(DomainEventType.UserRegistered, payload)

        assertEquals(domainEvent.type, DomainEventType.UserRegistered)
        assertEquals(domainEvent.payload, payload)
    }
}