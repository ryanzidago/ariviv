package com.ryanzidago.ariviv.repositories


import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User
import java.util.*

class UserRepository {
    fun createUser(user: User) {
        if (users.any { it.email == user.email }) {
            throw Exception("Email address already taken")
        } else {
            users.add(user)
        }
    }

    fun getUserByName(name: String): User? {
        return users.find { it.name == name }
    }

    fun getUserById(id: UUID): User? {
        return users.find { it.id == id }
    }

    fun listUsers(): List<User> {
        return users
    }
}