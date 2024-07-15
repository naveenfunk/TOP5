package com.example.top5.features.auth.data.repo

import com.example.top5.features.auth.data.models.toDomain
import com.example.top5.features.auth.data.remote.UserRemoteDataSource
import com.example.top5.features.auth.domain.models.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun login(username: String, password: String) {
        userRemoteDataSource.login(username, password)
    }

    override suspend fun signUp(username: String, password: String) {
        userRemoteDataSource.signUp(username, password)
    }

    override fun logout() {
        userRemoteDataSource.logout()
    }

    override fun getCurrentUser() : User? {
        return userRemoteDataSource.getCurrentUser()?.toDomain()
    }

    override fun isUserLoggedIn(): Boolean {
        return userRemoteDataSource.isUserSessionActive()
    }

}