package com.example.top5.features.movies.domain

import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.features.movies.data.models.MovieDto
import com.example.top5.features.movies.ui.models.SearchMovieItem

data class Movie(
    val id: String,
    val title: String,
    val image: String,
    val rank: Int?
)

fun Movie.toDto() = MovieDto(
    id = id,
    title = title,
    poster_path = image,
    rank = rank
)

fun Movie.toUi() = MovieListItem(
    id = id,
    title = title,
    imageUrl = image,
    rank = rank
)

fun Movie.toSearchedUi() = SearchMovieItem(
    id = id?: "",
    title = title,
    imageUrl = image,
)