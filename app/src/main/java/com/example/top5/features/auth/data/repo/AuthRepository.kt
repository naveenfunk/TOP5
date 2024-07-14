package com.example.top5.features.auth.data.repo

interface AuthRepository {

    suspend fun login(username: String, password: String)

    suspend fun signUp(username: String, password: String)

    fun logout()

    fun isUserLoggedIn() : Boolean

}