package com.example.top5.features.favorites.data.remote

import com.example.top5.features.favorites.data.model.FavoriteDto
import com.example.top5.features.favorites.domain.usecase.GetTopFiveMovieListUseCase
import com.example.top5.features.movies.data.models.MovieDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRemoteDataSourceImpl @Inject constructor() : FavoriteRemoteDataSource {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val favoriteCol = "movie_lists"
    private val userEmail = "userEmail"
    private val moviesField = "movies"
    private val idField = "id"

    override suspend fun createFavorite(favorite: FavoriteDto): String {
        val id = db.collection(favoriteCol).document().id
        db.collection(favoriteCol).add(favorite.copy(id = id, userEmail = favorite.userEmail.lowercase())).await()
        return id
    }

    override suspend fun getFavorites(userEmail: String): List<FavoriteDto> {
        val snapshot = db.collection(favoriteCol)
            .whereEqualTo(this.userEmail, userEmail)
            .get()
            .await()
        val movieLists = snapshot.toObjects(FavoriteDto::class.java)
        return movieLists
    }

    override suspend fun updateFavorite(favoriteId: String, emoji: String, title: String) {
        val movieListRef = getFavoriteById(favoriteId).reference

        db.runTransaction { transaction ->
            val favorite = transaction.get(movieListRef).toObject(FavoriteDto::class.java)
                ?: throw Exception("favorite not found")

            val updatedMovies = favorite.copy(emoji = emoji, title = title)
            transaction.set(movieListRef, updatedMovies)
        }.await()
    }

    override suspend fun deleteFavorite(favoriteId: String) {
        getFavoriteById(favoriteId).reference.delete().await()
    }

    private suspend fun getFavoriteById(favoriteId: String) =
        db.collection(favoriteCol).whereEqualTo(idField, favoriteId)
            .get()
            .await().documents.first()

    override suspend fun getFavoritesByUserEmail(email: String): List<FavoriteDto> {
        val movieListsSnapshot = db.collection(favoriteCol)
            .whereEqualTo(userEmail, email.lowercase())
            .get()
            .await()

        return movieListsSnapshot.toObjects(FavoriteDto::class.java)
    }

    override suspend fun isCurrentUserOwnsFavorite(favoriteId: String): Boolean {
        val currentUser = auth.currentUser
            ?: throw Exception("No user is currently logged in")

        val favoriteDoc = getFavoriteById(favoriteId)

        if (!favoriteDoc.exists()) {
            throw Exception("favorite not found")
        }

        val ownerId = favoriteDoc.getString(userEmail)
        return ownerId == currentUser.email
    }

    override suspend fun getFavorite(favoriteId: String): FavoriteDto {
        val documentSnapshot = getFavoriteById(favoriteId)

        if (!documentSnapshot.exists()) {
            throw Exception("favorite not found")
        }

        val favorite = documentSnapshot.toObject(FavoriteDto::class.java)

        if (favorite != null) {
            return favorite
        } else {
            throw Exception("Failed to parse favorite data")
        }
    }

    override suspend fun addMovieToFavorite(favoriteId: String, movie: MovieDto) {
        val favoriteRef = getFavoriteById(favoriteId).reference

        db.runTransaction { transaction ->
            val favorite = transaction.get(favoriteRef).toObject(FavoriteDto::class.java)
                ?: throw Exception("favorite not found")

            if (favorite.movies.size >= GetTopFiveMovieListUseCase.MAX_MOVIE_COUNT) {
                throw Exception("favorite already has max movies")
            }

            val updatedMovies = favorite.movies + movie
            transaction.update(favoriteRef, moviesField, updatedMovies)
        }.await()
    }

    override suspend fun deleteMovieFromFavorite(favoriteId: String, movieId: String) {
        val favoriteRef = getFavoriteById(favoriteId).reference

        db.runTransaction { transaction ->
            val favorite = transaction.get(favoriteRef).toObject(FavoriteDto::class.java)
                ?: throw Exception("favorite not found")

            val updatedMovies = favorite.movies.filter { it.id != movieId }
            transaction.update(favoriteRef, moviesField, updatedMovies)
        }.await()
    }

    override suspend fun updateMovieInFavorite(favoriteId: String, updatedMovie: MovieDto) {
        val movieListRef = getFavoriteById(favoriteId).reference

        db.runTransaction { transaction ->
            val favorite = transaction.get(movieListRef).toObject(FavoriteDto::class.java)
                ?: throw Exception("favorite not found")

            val updatedMovies = favorite.movies.map {
                if (it.id == updatedMovie.id) updatedMovie else it
            }
            transaction.update(movieListRef, moviesField, updatedMovies)
        }.await()
    }
}