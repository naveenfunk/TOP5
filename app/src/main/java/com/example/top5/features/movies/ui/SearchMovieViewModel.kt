package com.example.top5.features.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.favorites.domain.usecase.AddMovieToFavorite
import com.example.top5.features.favorites.ui.topFive.TopFiveFragment
import com.example.top5.features.movies.domain.GetMoviesByQuery
import com.example.top5.features.movies.ui.models.SearchMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMoviesByQuery: GetMoviesByQuery,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val favoriteId =
        stateHandle.get<String>(TopFiveFragment.FAVORITE_ID)

    private val movieRank =
        stateHandle.get<Int>(TopFiveFragment.MOVIE_RANK)

    private val _searchResults = MutableLiveData<List<SearchMovieItem>>(emptyList())
    val searchResults: MutableLiveData<List<SearchMovieItem>> = _searchResults

    private val searchExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _searchResults.postValue(emptyList())
    }

    private val otherExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO + searchExceptionHandler) {
            val movies = getMoviesByQuery(query = query)
            _searchResults.postValue(movies)
        }
    }

    fun onMovieClick(movieItem: SearchMovieItem, onActionComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + otherExceptionHandler) {
            if (favoriteId != null && movieRank != null) {
                addMovieToFavorite(favoriteId, movieItem, movieRank)
                withContext(Dispatchers.Main) { onActionComplete() }
            }
        }
    }
}