package com.example.top5.features.movies.ui.models

import com.example.top5.features.movies.data.models.SearchedMovie
import com.example.top5.features.movies.domain.Movie

data class SearchMovieItem(
    val id: String,
    val title: String,
    val imageUrl: String,
)

fun SearchMovieItem.toSearchedMovie() = SearchedMovie(
    id, title, imageUrl
)

fun SearchMovieItem.toDomain(rank: Int) = Movie(
    id, title, imageUrl, rank
)
