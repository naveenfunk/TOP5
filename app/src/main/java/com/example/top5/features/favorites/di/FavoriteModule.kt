package com.example.top5.features.favorites.di

import com.example.top5.features.favorites.data.remote.FavoriteRemoteDataSource
import com.example.top5.features.favorites.data.remote.FavoriteRemoteDataSourceImpl
import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.data.repo.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {

    @Binds
    @Singleton
    abstract fun provideFavoriteRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl) : FavoritesRepository

    @Binds
    @Singleton
    abstract fun provideFavoriteRemoteDataSource(favoriteRemoteDataSourceImpl: FavoriteRemoteDataSourceImpl) : FavoriteRemoteDataSource

}