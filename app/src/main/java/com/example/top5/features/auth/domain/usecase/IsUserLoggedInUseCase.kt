package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.data.repo.UserRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.isUserLoggedIn()
    }
}