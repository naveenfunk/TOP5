package com.example.top5.features.favorites.domain.models

import com.example.top5.features.favorites.data.model.FavoriteDto
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import com.example.top5.features.movies.domain.Movie
import com.example.top5.features.movies.domain.toDto

class Favorite(
    val id: String,
    val userEmail: String,
    val title: String,
    val emoji: String,
    val movies: List<Movie>
)

fun Favorite.toDto() = FavoriteDto(
    id = id,
    userEmail = userEmail,
    title = title,
    emoji = emoji,
    movies = this.movies.map { it.toDto() }
)

fun Favorite.toUi() = FavoriteListItem(
    id = id,
    title = title,
    emoji = emoji,
)