package com.example.top5.features.auth.di

import com.example.top5.features.auth.data.remote.UserRemoteDataSource
import com.example.top5.features.auth.data.remote.UserRemoteDataSourceImpl
import com.example.top5.features.auth.data.repo.UserRepository
import com.example.top5.features.auth.data.repo.UserRepositoryImpl
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
    abstract fun provideAuthRepository(authRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: UserRemoteDataSourceImpl) : UserRemoteDataSource
}