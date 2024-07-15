package com.example.top5.features.movies.data.repo

import com.example.top5.features.movies.domain.Movie

interface MovieRepository {
    suspend fun searchMovies(query: String): List<Movie>
}