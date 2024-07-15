package com.example.top5.features.movies.data.remote

import com.example.top5.features.movies.data.models.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie?api_key=dab3bfd4d3a68c032b57e2c6bb941644&include_adult=false&language=en-US&page=1")
    suspend fun searchMovies(@Query("query") query: String): MovieSearchResponse
}