package com.example.top5.features.movies.di

import com.example.top5.features.movies.data.remote.MovieRemoteDataSource
import com.example.top5.features.movies.data.remote.MovieRemoteDataSourceImpl
import com.example.top5.features.movies.data.repo.MovieRepository
import com.example.top5.features.movies.data.repo.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}