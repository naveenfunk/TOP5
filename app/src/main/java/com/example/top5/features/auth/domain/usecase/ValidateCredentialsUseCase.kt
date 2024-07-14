package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.domain.models.BaseAuthException
import javax.inject.Inject

class ValidateCredentialsUseCase @Inject constructor() {

    operator fun invoke(username: String, password: String): Boolean {
        if (username.isBlank()) throw BaseAuthException.EmptyUsernameException()
        if (password.isBlank()) throw BaseAuthException.EmptyPasswordException()
        if (password.length < 6) throw BaseAuthException.TooShortPasswordException()
        return true
    }
}
