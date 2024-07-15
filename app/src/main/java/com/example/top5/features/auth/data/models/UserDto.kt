package com.example.top5.features.auth.data.models

import com.example.top5.features.auth.domain.models.User

data class UserDto(
    val id: String,
    val email: String
)

fun UserDto.toDomain() = User(id = id, email = email)
