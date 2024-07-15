package com.example.top5.features.auth.domain.models

sealed class SignUpException(message: String) : BaseAuthException(errorText = message) {
    class WeakPasswordException(message: String = "Weak password") : SignUpException(message = message)
    class UserAlreadyExistException(message: String = "user already exists") : SignUpException(message = message)
    class MismatchPasswordsException(message: String = "password doesn't match with confirm password") : SignUpException(message = message)
}