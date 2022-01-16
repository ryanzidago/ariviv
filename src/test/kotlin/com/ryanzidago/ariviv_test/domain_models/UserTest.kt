package com.ryanzidago.ariviv_test.domain_models

import org.junit.Test
import kotlin.test.assertEquals
import com.ryanzidago.ariviv.domain_models.User
import java.util.*

class UserTest {
    @Test
    fun userInstanceCanBeCreated() {
        val user = User("Ryan",  "ryan@email.fr")
        assertEquals(user.name, "Ryan")
        assertEquals(user.email, "ryan@email.fr")
        assert(user.id is UUID)
    }
}

