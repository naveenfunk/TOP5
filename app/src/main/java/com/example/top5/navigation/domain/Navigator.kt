package com.example.top5.navigation.domain

interface Navigator {

    fun navigateFromSignUpToLogin()

    fun navigateFromLoginToSignUp()

    fun navigateBack()
}