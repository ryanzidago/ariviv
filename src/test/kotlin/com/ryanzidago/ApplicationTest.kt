package com.ryanzidago

import com.ryanzidago.ariviv.data.defaultUsers
import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.ryanzidago.plugins.*

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
        val expectedResponse = "{\"data\":{\"markExerciseSessionAsFinished\":\"Congrats Jean! You finished a session full of exercises!\"}}"
        val markExerciseSessionAsFinishedMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  markExerciseSessionAsFinished(name: \\\"Jean\\\")\\n}\\n\"}"

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
    fun errorIsThrownIfNoUserCouldBeFoundWhenAttemptingToMarkAnExerciseSessionAsFinished() = withTestApplication(Application::module) {
        users = defaultUsers()

        val markExerciseSessionAsFinishedMutation = "{\"operationName\":null,\"variables\":{},\"query\":\"mutation {\\n  markExerciseSessionAsFinished(name: \\\"Wolfgang\\\")\\n}\\n\"}"
        val expectedResponse = "{\"errors\":[{\"message\":\"No user with name Wolfgang could be found\",\"locations\":[{\"line\":2,\"column\":3}],\"path\":[]}]}"

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