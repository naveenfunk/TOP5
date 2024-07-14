package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.data.repo.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(){
        return authRepository.logout()
    }
}