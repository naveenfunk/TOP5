package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.domain.models.toUi
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(userEmail: String): List<FavoriteListItem> {
        return favoritesRepository.getFavorites(userEmail = userEmail).map { it.toUi() }
    }
}