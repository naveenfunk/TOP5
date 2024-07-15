package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.domain.models.Favorite
import javax.inject.Inject

class GetSingleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favoriteId: String): Favorite {
        return favoritesRepository.getFavorite(favoriteId = favoriteId)
    }
}