package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.data.repo.UserRepository
import com.example.top5.features.auth.domain.models.User
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): User? {
        return userRepository.getCurrentUser()
    }
}