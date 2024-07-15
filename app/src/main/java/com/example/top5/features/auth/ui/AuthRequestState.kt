package com.example.top5.features.auth.ui

sealed class AuthRequestState {
    object Loading: AuthRequestState()
    object Success: AuthRequestState()
    data class Error(val exception: Exception) : AuthRequestState()
}