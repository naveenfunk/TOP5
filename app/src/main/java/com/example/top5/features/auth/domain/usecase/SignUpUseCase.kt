package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.data.repo.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) {
        authRepository.signUp(username, password)
    }
}