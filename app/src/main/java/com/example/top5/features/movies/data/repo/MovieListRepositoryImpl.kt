package com.example.top5.features.movies.data.repo

import com.example.top5.features.movies.data.models.toDomain
import com.example.top5.features.movies.data.remote.MovieRemoteDataSource
import com.example.top5.features.movies.domain.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun searchMovies(query: String): List<Movie> {
        return movieRemoteDataSource.searchMovies(query).map { it.toDomain() }
    }
}