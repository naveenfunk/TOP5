package com.example.top5.features.auth.data.remote

import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.LoginException
import com.example.top5.features.auth.domain.models.SignUpException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor() : AuthRemoteDataSource {

    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun login(username: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(username, password).await()
        } catch (e: Exception) {
            throw when (e) {
                is FirebaseAuthInvalidCredentialsException -> LoginException.InvalidCredentialsException()
                is FirebaseAuthInvalidUserException -> LoginException.InvalidUserException()
                else -> BaseAuthException.UnknownException()
            }
        }

    }

    override suspend fun signUp(username: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(username, password).await()
        } catch (e: Exception) {
            throw when (e) {
                is FirebaseAuthUserCollisionException -> SignUpException.UserAlreadyExistException()
                is FirebaseAuthWeakPasswordException -> SignUpException.WeakPasswordException()
                else -> BaseAuthException.UnknownException()
            }
        }
    }

    override fun isUserSessionActive(): Boolean {
        return auth.currentUser != null
    }

    override fun logout() {
        auth.signOut()
    }
}