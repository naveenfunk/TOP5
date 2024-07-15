package com.example.top5.features.movies.data.models

import com.example.top5.features.movies.domain.Movie

data class MovieDto(
    val id: String = "",
    val title: String = "",
    val poster_path: String = "",
    val rank: Int? = null
)

fun MovieDto.toDomain() = Movie(
    id,
    title,
    poster_path,
    rank = rank
)