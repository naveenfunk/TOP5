package com.example.top5.features.favorites.data.model

import com.example.top5.features.favorites.domain.models.Favorite
import com.example.top5.features.movies.data.models.MovieDto
import com.example.top5.features.movies.data.models.toDomain

data class FavoriteDto(
    val id: String = "",
    val userEmail: String = "",
    val title: String = "",
    val emoji: String = "",
    val movies: List<MovieDto> = emptyList()
)

data class FavoriteDtoTest(
    val id: String,
    val userId: String,
    val title: String,
    val emoji: String,
)

fun FavoriteDto.toDomain() = Favorite(
    id,
    userEmail = userEmail,
    title,
    emoji,
    movies = movies.map { it.toDomain() }
)