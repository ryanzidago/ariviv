package com.ryanzidago.ariviv_test.application_services

import com.ryanzidago.ariviv.application_services.MarkExerciseSessionAsFinishedService
import com.ryanzidago.ariviv.data.*
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith


class MarkExerciseSessionAsFinishedServiceTest {
    @Test
    fun performFunctionUpdatesTheStateHashMapWithAUserIDAndTheTimeWhenTheExerciseWasFinished() {
        assert(state.isEmpty())

        MarkExerciseSessionAsFinishedService().perform(julieUUID)

        assert(state[julieUUID] is LocalDateTime)

        state = defaultState()
        domainEvents = defaultDomainEvents()
    }

    @Test
    fun performFunctionSavesADomainEvent() {
        assert(domainEvents.isEmpty())

        MarkExerciseSessionAsFinishedService().perform(julieUUID)

        assert(domainEvents.isNotEmpty())

        state = defaultState()
        domainEvents = defaultDomainEvents()
    }

    @Test
    fun throwsAnExceptionIfNoUserCanBeFoundGivenTheId() {
        assertFailsWith<Exception> {
            MarkExerciseSessionAsFinishedService().perform(UUID.randomUUID())
        }
    }
}