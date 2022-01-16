package com.ryanzidago.ariviv_test.application_services

import com.ryanzidago.ariviv.application_services.RegisterUserService
import com.ryanzidago.ariviv.data.defaultUsers
import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RegisterUserServiceTest {
    @Test
    fun performFunctionSavesAUser() {
        RegisterUserService().perform("Ryan", "ryan@email.fr")

        val user = UserRepository().getUserByEmail("ryan@email.fr")

        assertEquals(user?.name, "Ryan")
        assertEquals(user?.email, "ryan@email.fr")

        users = defaultUsers()
    }

    @Test
    fun performFunctionThrowsAnExceptionIfAUserWithTheSameEmailAlreadyExists() {
        assertFailsWith<Exception> {
            RegisterUserService().perform("Ryan", "jean@email.fr")
        }
    }
}