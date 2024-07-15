package com.example.top5.features.favorites.data.repo

import com.example.top5.features.favorites.domain.models.Favorite
import com.example.top5.features.movies.domain.Movie

interface FavoritesRepository {

    suspend fun createFavorite(favorite: Favorite) : String
    suspend fun getFavorites(userEmail: String): List<Favorite>
    suspend fun updateFavorite(favoriteId: String, emoji: String, title: String)
    suspend fun deleteFavorite(favoriteId: String)
    suspend fun getFavoritesByUserEmail(email: String): List<Favorite>
    suspend fun isCurrentUserOwnsFavorite(favoriteId: String): Boolean
    suspend fun getFavorite(favoriteId: String): Favorite
    suspend fun addMovieToFavorite(favoriteId: String, movie: Movie)
    suspend fun deleteMovieFromFavorite(favoriteId: String, movieId: String)
    suspend fun updateMovieInFavorite(favoriteId: String, movie: Movie)
}