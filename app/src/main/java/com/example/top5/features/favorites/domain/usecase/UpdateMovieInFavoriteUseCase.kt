package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.features.favorites.ui.models.toDomain
import javax.inject.Inject

class UpdateMovieInFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favoriteId: String, movie: MovieListItem) {
        favoritesRepository.updateMovieInFavorite(favoriteId, movie.toDomain())
    }
}