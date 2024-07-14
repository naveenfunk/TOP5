package com.example.top5.features.auth.domain.models

sealed class LoginException(errorText: String) : BaseAuthException(errorText = errorText) {
    class InvalidCredentialsException(errorText: String = "invalid credentials") : LoginException(errorText = errorText)
    class InvalidUserException(errorText: String = "invalid user. No user found") : LoginException(errorText = errorText)
}