package com.example.top5.features.movies.domain

import com.example.top5.features.movies.data.repo.MovieRepository
import com.example.top5.features.movies.ui.models.SearchMovieItem
import javax.inject.Inject

class GetMoviesByQuery @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(query: String): List<SearchMovieItem> {
        return movieRepository.searchMovies(query = query).map { it.toSearchedUi() }
    }
}