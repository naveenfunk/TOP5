package com.example.top5.navigation.domain

import com.example.top5.features.favorites.domain.models.TopFiveType

interface Navigator {

    fun navigateFromSignUpToLogin()

    fun navigateFromLoginToSignUp()

    fun navigateFromLoginToHome()

    fun navigateBack()

    fun setHomeAsStartDestination()

    fun navigateFromHomeToLogin()

    fun navigateFromHomeToTopFive(favoriteId: String?)

    fun navigateFromTopFiveToSearchMovie(favoriteId: String, rank: Int)

    fun navigateFromViewableProfileToTopFive(favoriteId: String)

    fun navigateFromHomeToViewableProfile()
}