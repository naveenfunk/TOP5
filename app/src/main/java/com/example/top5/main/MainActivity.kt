package com.example.top5.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.top5.R
import com.example.top5.databinding.ActivityMainBinding
import com.example.top5.navigation.domain.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    private val mainActivityViewModel : MainActivityViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (mainActivityViewModel.isUserLoggedIn()) {
            navigator.setHomeAsStartDestination()
        }
    }
}