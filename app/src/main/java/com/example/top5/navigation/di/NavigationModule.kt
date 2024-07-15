package com.example.top5.navigation.di

import android.content.Context
import com.example.top5.navigation.domain.Navigator
import com.example.top5.navigation.domain.impl.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigator(@ActivityContext context: Context) : Navigator {
        return NavigatorImpl(context)
    }

}