package com.example.top5.main

import androidx.lifecycle.ViewModel
import com.example.top5.features.auth.domain.usecase.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    fun isUserLoggedIn() = isUserLoggedInUseCase()
}