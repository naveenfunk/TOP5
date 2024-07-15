package com.example.top5.features.favorites.data.remote

import com.example.top5.features.favorites.data.model.FavoriteDto
import com.example.top5.features.movies.data.models.MovieDto

interface FavoriteRemoteDataSource {
    suspend fun createFavorite(favorite: FavoriteDto): String
    suspend fun updateFavorite(favoriteId: String, emoji: String, title: String)
    suspend fun getFavorites(userEmail: String): List<FavoriteDto>
    suspend fun deleteFavorite(favoriteId: String)
    suspend fun getFavorite(favoriteId: String): FavoriteDto
    suspend fun getFavoritesByUserEmail(email: String): List<FavoriteDto>
    suspend fun isCurrentUserOwnsFavorite(favoriteId: String): Boolean
    suspend fun addMovieToFavorite(favoriteId: String, movie: MovieDto)
    suspend fun deleteMovieFromFavorite(favoriteId: String, movieId: String)
    suspend fun updateMovieInFavorite(favoriteId: String, updatedMovie: MovieDto)
}