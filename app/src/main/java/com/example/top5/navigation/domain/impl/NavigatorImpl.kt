package com.example.top5.navigation.domain.impl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.top5.R
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

    override fun navigateFromLoginToSignUp() {
        navController.navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    override fun navigateFromLoginToHome() {
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }
}