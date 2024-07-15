package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.domain.models.Favorite
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favorite: Favorite) {
        favoritesRepository.updateFavorite(favorite = favorite)
    }
}