package com.example.top5.features.auth.data.repo

import com.example.top5.features.auth.data.remote.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(username: String, password: String) {
        authRemoteDataSource.login(username, password)
    }

    override suspend fun signUp(username: String, password: String) {
        authRemoteDataSource.signUp(username, password)
    }

    override suspend fun logout(userId: String) {

    }
}