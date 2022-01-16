package com.ryanzidago

import com.ryanzidago.ariviv.data.*
import com.ryanzidago.ariviv.domain_events.DomainEvent
import com.ryanzidago.ariviv.domain_events.DomainEventType
import io.ktor.http.*
import io.ktor.application.*
import kotlin.test.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.collections.HashMap

class ApplicationTest {
    @Test
    fun graphQLDocumentationIsReachable() {
        val expectedTitlePage = "Graphql schema documentation"

        withTestApplication(Application::module){
            handleRequest(HttpMethod.Get, "/graphql/doc/index.html").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assert(response.content!!.contains(expectedTitlePage))
            }
        }
    }
    @Test
    fun graphQLPlaygroundIsReachable() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/graphql").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assert(response.content!!.contains("GraphQL Playground"))
            }
        }
    }

    @Test
    fun usersCanBeQueriedViaGraphQL() = withTestApplication(Application::module) {
        val expectedResponse = "{\"data\":{\"users\":[{\"name\":\"Jean\"},{\"name\":\"Günther\"},{\"name\":\"Julie\"}]}}"
        val usersQuery = "{\"operationName\":null,\"variables\":{},\"query\":\"{\n  users {\n    name\n  }\n}\n\"}"

        with(handleRequest(HttpMethod.Post, "/graphql"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(usersQuery)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(response.content!!, expectedResponse)
        }
        users = defaultUsers()
    }

    @Test
    fun userCanBeRegisteredViaGraphQL() = withTestApplication(Application::module) {
        val expectedResponse = "{\"data\":{\"registerUser\":{\"name\":\"Sophie\",\"email\":\"sophie@email.fr\"}}}"
        val createUserMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  registerUser(name: \\\"Sophie\\\", email: \\\"sophie@email.fr\\\") {\\n    name\\n    email\\n  }\\n}\\n\"}"

        with(handleRequest(HttpMethod.Post, "/graphql") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(createUserMutation)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(response.content!!, expectedResponse)
        }
        users = defaultUsers()
    }

    @Test
    fun exerciseSessionsCanBeMarkedAsFinishedViaGraphQL() = withTestApplication(Application::module) {
        val expectedResponse = "{\"data\":{\"markExerciseSessionAsFinished\":\"User with id $jeanUUID has finished a session full of exercises!\"}}"
        val markExerciseSessionAsFinishedMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  markExerciseSessionAsFinished(id: \\\"${jeanUUID}\\\")\\n}\\n\"}"

        with(handleRequest(HttpMethod.Post, "/graphql") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(markExerciseSessionAsFinishedMutation)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(response.content!!, expectedResponse)
        }
        users = defaultUsers()
    }

    @Test
    fun usersReceiveReminderToExerciseAfterTheyHaveMarkedAnExerciseSessionAsFinishedAndTheyHaveNotExercisesRecently() = withTestApplication(Application::module) {
        val markExerciseSessionAsFinishedMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  markExerciseSessionAsFinished(id: \\\"${jeanUUID}\\\")\\n}\\n\"}"
        val payload = HashMap<Any, Any>()
        payload["name"] = "Jean"
        payload["email"] = "jean@email.fr"
        payload["id"] = jeanUUID
        val expectedDomainEvent = DomainEvent(DomainEventType.ReminderToStartExerciseSessionSent, payload)

        with(handleRequest(HttpMethod.Post, "/graphql") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(markExerciseSessionAsFinishedMutation)
        }) {
            Thread.sleep(100)
            assert(domainEvents.contains(expectedDomainEvent))
        }
    }

    @Test
    fun errorIsThrownIfNoUserCouldBeFoundWhenAttemptingToMarkAnExerciseSessionAsFinished() = withTestApplication(Application::module) {
        val uuid = UUID.randomUUID()
        val markExerciseSessionAsFinishedMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  markExerciseSessionAsFinished(id: \\\"${uuid}\\\")\\n}\\n\"}"
        val expectedResponse = "{\"errors\":[{\"message\":\"No user with id $uuid could be found\",\"locations\":[{\"line\":2,\"column\":3}],\"path\":[]}]}"

        with(handleRequest(HttpMethod.Post, "/graphql") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(markExerciseSessionAsFinishedMutation)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(response.content!!, expectedResponse)
        }
        users = defaultUsers()

    }
}