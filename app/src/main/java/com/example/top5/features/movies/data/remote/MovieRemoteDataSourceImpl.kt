package com.example.top5.features.movies.data.remote

import com.example.top5.features.movies.data.models.SearchedMovie
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRemoteDataSource {

    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

    override suspend fun searchMovies(query: String): List<SearchedMovie> {
        return movieApiService.searchMovies(query).results.map { it.copy(poster_path = imageBaseUrl + it.poster_path) }
    }
}