package com.example.top5.features.favorites.domain.usecase

import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.features.favorites.domain.usecase.GetTopFiveMovieListUseCase.Companion.MAX_MOVIE_COUNT
import javax.inject.Inject

class CreateNewAddMovieListUseCase @Inject constructor() {

    suspend operator fun invoke(): List<MovieListItem> {
        return List(MAX_MOVIE_COUNT) { MovieListItem() }
    }
}