package com.ryanzidago.ariviv_test.repositories

import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User
import kotlin.test.Test

class UserRepositoryTest {
    @Test
    fun createUserTest() {
        val user = User("Hyacinthe", "hyacinthe@email.fr")
        users.add(user)
        assert(users.contains(user))
    }
}   