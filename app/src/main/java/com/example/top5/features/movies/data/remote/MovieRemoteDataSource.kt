package com.example.top5.features.movies.data.remote

import com.example.top5.features.movies.data.models.SearchedMovie

interface MovieRemoteDataSource {
    suspend fun searchMovies(query: String): List<SearchedMovie>
}