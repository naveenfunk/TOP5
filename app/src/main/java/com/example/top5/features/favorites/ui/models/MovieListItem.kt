package com.example.top5.features.favorites.ui.models

import com.example.top5.features.favorites.domain.models.TopFiveType
import com.example.top5.features.movies.domain.Movie

data class MovieListItem(
    val id: String = "",
    val title: String? = null,
    val topFiveType: TopFiveType = TopFiveType.CREATE_TOP_FIVE,
    val rank: Int? = null,
    val imageUrl: String? = null
)

fun MovieListItem.toDomain() = Movie(
    id = id,
    title ?: "",
    imageUrl ?: "",
    rank
)
