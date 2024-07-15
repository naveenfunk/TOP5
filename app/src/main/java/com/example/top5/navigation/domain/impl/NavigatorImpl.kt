package com.example.top5.navigation.domain.impl

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.top5.R
import com.example.top5.features.favorites.ui.topFive.TopFiveFragment.Companion.FAVORITE_ID
import com.example.top5.features.favorites.ui.topFive.TopFiveFragment.Companion.MOVIE_RANK
import com.example.top5.navigation.domain.Navigator
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    @ActivityContext private val context: Context
) : Navigator {

    private val navController: NavController
        get() = (context as AppCompatActivity).findNavController(R.id.navHost)

    override fun navigateFromSignUpToLogin() {
        navController.navigate(R.id.action_signUpFragment_to_loginFragment)
    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun setHomeAsStartDestination() {
        val navHostFragment = (context as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.navHost) as NavHostFragment

        val navController = navHostFragment.navController

        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.app_nav)

        navGraph.setStartDestination(R.id.profileFragment)

        navController.graph = navGraph
    }

    override fun navigateFromLoginToSignUp() {
        navController.navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    override fun navigateFromHomeToViewableProfile() {
        navController.navigate(R.id.action_profileFragment_to_viewableProfileFragment)
    }

    override fun navigateFromHomeToTopFive(favoriteId: String?) {
        navController.navigate(
            R.id.action_profileFragment_to_topFiveFragment,
            Bundle().apply { putString(FAVORITE_ID, favoriteId) })
    }

    override fun navigateFromViewableProfileToTopFive(favoriteId: String) {
        navController.navigate(
            R.id.action_viewableProfileFragment_to_topFiveFragment,
            Bundle().apply { putString(FAVORITE_ID, favoriteId) })
    }

    override fun navigateFromTopFiveToSearchMovie(favoriteId: String, rank: Int) {
        navController.navigate(
            R.id.action_topFiveFragment_to_searchMovieFragment,
            Bundle().apply {
                putString(FAVORITE_ID, favoriteId)
                putInt(MOVIE_RANK, rank)
            })
    }

    override fun navigateFromLoginToHome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()

        navController.navigate(
            R.id.action_loginFragment_to_profileFragment,
            null,
            navOptions = navOptions
        )
    }

    override fun navigateFromHomeToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()

        navController.navigate(
            R.id.action_profileFragment_to_loginFragment,
            args = null,
            navOptions = navOptions
        )
    }
}