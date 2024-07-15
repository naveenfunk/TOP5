package com.example.top5.features.auth.data.repo

import com.example.top5.features.auth.domain.models.User

interface UserRepository {

    suspend fun login(username: String, password: String)

    suspend fun signUp(username: String, password: String)

    fun logout()

    fun isUserLoggedIn() : Boolean

    fun getCurrentUser(): User?
}