package com.ryanzidago.ariviv_test.repositories

import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User
import com.ryanzidago.ariviv.repositories.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryTest {
    @Test
    fun createUserTest() {
        val user = User("Hyacinthe", "hyacinthe@email.fr")
        UserRepository().createUser(user)
        assert(users.contains(user))
    }

    @Test
    fun getUserByNameTest() {
        val expectedUser = User("Hyacinthe", "hyacinthe@email.fr")
        UserRepository().createUser(expectedUser)

        val user = UserRepository().getUserByName("Hyacinthe")
        assertEquals(user, expectedUser)

        val nonExistingUser = UserRepository().getUserByName("Hugo")
        assert(nonExistingUser == null)
    }

    @Test
    fun listUsersTest() {
        val users = UserRepository().listUsers()
        assert(users is List<User>)
    }
}   