package com.example.top5.features.auth.domain.models

sealed class RequestStatus<out D> {
    data class Success<D>(val data: D) : RequestStatus<D>()
    data class Error(val error: Exception) : RequestStatus<Unit>()
}