package com.example.top5.features.auth.data.remote

import com.example.top5.features.auth.data.models.UserDto

interface UserRemoteDataSource {

    suspend fun login(username: String, password: String)

    fun getCurrentUser(): UserDto?

    suspend fun signUp(username: String, password: String)

    fun isUserSessionActive() : Boolean

    fun logout()
}