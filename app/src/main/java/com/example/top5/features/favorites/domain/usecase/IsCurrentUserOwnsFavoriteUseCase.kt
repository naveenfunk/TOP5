package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import javax.inject.Inject

class IsCurrentUserOwnsFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favoriteId : String) : Boolean {
        return favoritesRepository.isCurrentUserOwnsFavorite(favoriteId)
    }
}