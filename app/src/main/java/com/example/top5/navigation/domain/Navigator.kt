package com.example.top5.navigation.domain

interface Navigator {

    fun navigateFromSignUpToLogin()

    fun navigateFromLoginToSignUp()

    fun navigateFromLoginToHome()

    fun navigateBack()

    fun setHomeAsStartDestination()

    fun navigateFromHomeToLogin()
}