package com.example.top5.features.favorites.data.repo

import com.example.top5.features.favorites.data.model.toDomain
import com.example.top5.features.favorites.data.remote.FavoriteRemoteDataSource
import com.example.top5.features.favorites.domain.models.Favorite
import com.example.top5.features.favorites.domain.models.toDto
import com.example.top5.features.movies.domain.Movie
import com.example.top5.features.movies.domain.toDto
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource
) : FavoritesRepository {
    override suspend fun createFavorite(favorite: Favorite) : String{
        return favoriteRemoteDataSource.createFavorite(favorite.toDto())
    }

    override suspend fun getFavorites(userEmail: String): List<Favorite> {
        return favoriteRemoteDataSource.getFavorites(userEmail).map { it.toDomain() }
    }

    override suspend fun updateFavorite(favorite: Favorite) {
        favoriteRemoteDataSource.updateFavorite(favorite.toDto())
    }

    override suspend fun deleteFavorite(favoriteId: String) {
        favoriteRemoteDataSource.deleteFavorite(favoriteId)
    }

    override suspend fun getFavoritesByUserEmail(email: String): List<Favorite> {
        return favoriteRemoteDataSource.getFavoritesByUserEmail(email).map { it.toDomain() }
    }

    override suspend fun isCurrentUserOwnsFavorite(favoriteId: String): Boolean {
        return favoriteRemoteDataSource.isCurrentUserOwnsFavorite(favoriteId)
    }

    override suspend fun getFavorite(favoriteId: String): Favorite {
        return favoriteRemoteDataSource.getFavorite(favoriteId).toDomain()
    }

    override suspend fun addMovieToFavorite(favoriteId: String, movie: Movie) {
        favoriteRemoteDataSource.addMovieToFavorite(favoriteId, movie = movie.toDto())
    }

    override suspend fun deleteMovieFromFavorite(favoriteId: String, movieId: String) {
        favoriteRemoteDataSource.deleteMovieFromFavorite(favoriteId, movieId)
    }

    override suspend fun updateMovieInFavorite(favoriteId: String, movie: Movie) {
        favoriteRemoteDataSource.updateMovieInFavorite(favoriteId, movie.toDto())
    }
}

