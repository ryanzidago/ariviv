package com.ryanzidago.ariviv.repositories

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.getLogger

class DomainEventRepository {
    private val logger = getLogger()

    fun appendDomainEvent(domainEvent: DomainEvent) {
        domainEvents.add(domainEvent)

        logger.debug("DomainEvent appended: $domainEvent")
    }
}