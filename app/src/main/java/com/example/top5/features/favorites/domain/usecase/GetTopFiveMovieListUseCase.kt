package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.auth.domain.usecase.GetCurrentUserUseCase
import com.example.top5.features.favorites.domain.models.TopFiveType
import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.features.movies.domain.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopFiveMovieListUseCase @Inject constructor(
    private val isCurrentUserOwnsFavoriteUseCase: IsCurrentUserOwnsFavoriteUseCase,
    private val getSingleFavoriteUseCase: GetSingleFavoriteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createNewAddMovieListUseCase: CreateNewAddMovieListUseCase
) {
    companion object {
        const val MAX_MOVIE_COUNT = 5
    }

    suspend operator fun invoke(favoriteId: String?): List<MovieListItem> {
        val currentUser = getCurrentUserUseCase()
        if (favoriteId != null && currentUser?.id != null) {
            val isCurrentUserFavorite = runCatching { isCurrentUserOwnsFavoriteUseCase(favoriteId) }.getOrNull() ?: false
            val sortedList = runCatching {
                getSingleFavoriteUseCase(favoriteId).movies.map { it.toUi() }
                    .sortedWith(compareBy({ it.rank }, { it.title }))
            }.getOrNull() ?: emptyList()

            val favoriteList = mutableListOf<MovieListItem>()

            return withContext(Dispatchers.Default) {
                // Fill in the favorite list
                for (rank in 1..MAX_MOVIE_COUNT) {
                    val item = sortedList.find { it.rank == rank }
                    if (item != null) {
                        val finalItem =
                            item.copy(topFiveType = if (isCurrentUserFavorite) TopFiveType.EDITABLE_TOP_FIVE else TopFiveType.READABLE_TOP_FIVE)
                        favoriteList.add(finalItem)
                    } else if (isCurrentUserFavorite) {
                        favoriteList.add(MovieListItem())
                    }
                }

                // If owned by user and there are less than MAX_MOVIE_COUNT ranked items, fill remaining slots with unranked items
                if (isCurrentUserFavorite && favoriteList.count { it.rank != null } < MAX_MOVIE_COUNT) {
                    val unrankedItems = sortedList.filter { it.rank == null }
                    var index = 0
                    for (i in favoriteList.size until MAX_MOVIE_COUNT) {
                        if (index < unrankedItems.size) {
                            favoriteList.add(unrankedItems[index])
                            index++
                        } else {
                            favoriteList.add(MovieListItem())
                        }
                    }
                }
                return@withContext favoriteList
            }
        } else {
            return createNewAddMovieListUseCase()
        }
    }
}