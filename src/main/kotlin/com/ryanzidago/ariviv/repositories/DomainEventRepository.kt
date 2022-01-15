package com.ryanzidago.ariviv.repositories

import com.ryanzidago.ariviv.data.domainEvents
import com.ryanzidago.ariviv.domain_events.DomainEvent

class DomainEventRepository() {
    fun appendDomainEvent(domainEvent: DomainEvent) {
        domainEvents.add(domainEvent)
    }
}