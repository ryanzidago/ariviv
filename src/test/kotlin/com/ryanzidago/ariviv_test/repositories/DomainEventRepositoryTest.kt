package com.ryanzidago.ariviv_test.repositories

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import com.ryanzidago.ariviv.repositories.DomainEventRepository
import kotlin.test.Test

class DomainEventRepositoryTest {
    @Test
    fun appendDomainEventTest() {
        val domainEvent = DomainEvent(DomainEventType.UserRegistered, HashMap<Any, Any>())
        DomainEventRepository().appendDomainEvent(domainEvent)

        assert(domainEvents.contains(domainEvent))
    }
}