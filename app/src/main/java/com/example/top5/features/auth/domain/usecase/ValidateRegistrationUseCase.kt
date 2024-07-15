package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.SignUpException
import javax.inject.Inject

class ValidateRegistrationUseCase @Inject constructor(){

    operator fun invoke(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isBlank()) throw BaseAuthException.EmptyUsernameException()
        if (password.isBlank()) throw BaseAuthException.EmptyPasswordException()
        if (password.length < 6) throw BaseAuthException.TooShortPasswordException()
        if (password != confirmPassword) throw SignUpException.MismatchPasswordsException()
        return true
    }
}