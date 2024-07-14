package com.example.top5.features.auth.di

import com.example.top5.features.auth.data.remote.AuthRemoteDataSource
import com.example.top5.features.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.top5.features.auth.data.repo.AuthRepository
import com.example.top5.features.auth.data.repo.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: AuthRemoteDataSourceImpl) : AuthRemoteDataSource
}