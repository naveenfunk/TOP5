package com.example.top5.features.auth.domain.usecase

import com.example.top5.features.auth.data.repo.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String){
        userRepository.login(username, password)
    }
}