package com.ryanzidago.ariviv.repositories


import com.ryanzidago.ariviv.data.users
import com.ryanzidago.ariviv.domain_models.User

class UserRepository {
    fun createUser(user: User) {
        users.add(user)
    }

    fun listUsers(): List<User> {
        return users
    }
}