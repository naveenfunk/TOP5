package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.movies.ui.models.SearchMovieItem
import com.example.top5.features.movies.ui.models.toDomain
import javax.inject.Inject

class AddMovieToFavorite @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favoriteId: String, movie: SearchMovieItem, rank: Int) {
        favoritesRepository.addMovieToFavorite(favoriteId, movie.toDomain(rank))
    }
}