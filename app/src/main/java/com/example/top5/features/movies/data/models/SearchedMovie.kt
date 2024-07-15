package com.example.top5.features.movies.data.models

import com.example.top5.features.movies.domain.Movie

data class SearchedMovie(
    val id: String,
    val title: String,
    val poster_path: String,
)

fun SearchedMovie.toDomain() = Movie(
    id,
    title,
    image = poster_path,
    rank = null
)
