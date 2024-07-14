package com.example.top5.features.auth.data.remote

interface AuthRemoteDataSource {

    suspend fun login(username: String, password: String)

    suspend fun signUp(username: String, password: String)
}