package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.data.repo.FavoritesRepository
import com.example.top5.features.favorites.domain.models.toUi
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import javax.inject.Inject

class GetFavoritesByUserEmailUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(email: String): List<FavoriteListItem> {
        return favoritesRepository.getFavoritesByUserEmail(email = email).map { it.toUi() }
    }
}