package com.example.top5.features.favorites.ui.topFive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.auth.domain.usecase.GetCurrentUserUseCase
import com.example.top5.features.favorites.domain.models.Favorite
import com.example.top5.features.favorites.domain.models.toUi
import com.example.top5.features.favorites.domain.usecase.CreateFavoriteUseCase
import com.example.top5.features.favorites.domain.usecase.DeleteMovieFromFavoriteUseCase
import com.example.top5.features.favorites.domain.usecase.GetSingleFavoriteUseCase
import com.example.top5.features.favorites.domain.usecase.GetTopFiveMovieListUseCase
import com.example.top5.features.favorites.domain.usecase.UpdateFavoriteUseCase
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import com.example.top5.features.favorites.ui.models.MovieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopFiveViewModel @Inject constructor(
    private val getTopFiveMovieListUseCase: GetTopFiveMovieListUseCase,
    private val deleteMovieFromFavoriteUseCase: DeleteMovieFromFavoriteUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val getSingleFavoriteUseCase: GetSingleFavoriteUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var favoriteId: String? =
        savedStateHandle.get<String>(TopFiveFragment.FAVORITE_ID)

    private val _movieList = MutableLiveData<List<MovieListItem>>(emptyList())
    val movieList: LiveData<List<MovieListItem>> = _movieList

    private val _favoriteData = MutableLiveData<FavoriteListItem>()
    val favoriteDat: LiveData<FavoriteListItem> = _favoriteData

    private val _isOnlyViewable = MutableLiveData<Boolean>(favoriteId == null)
    val isOnlyViewable: LiveData<Boolean> = _isOnlyViewable

    private val movieListExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _movieList.postValue(emptyList())
    }

    private val otherOperationExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun getFavoriteId() = favoriteId

    fun deleteMovie(movie: MovieListItem) {
        viewModelScope.launch(Dispatchers.IO + otherOperationExceptionHandler) {
            favoriteId?.let { deleteMovieFromFavoriteUseCase(it, movie.id) }
            refreshData()
        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO + movieListExceptionHandler) {
            val movieList = getTopFiveMovieListUseCase(favoriteId)
            _movieList.postValue(movieList)
            favoriteId?.let {
                val fav = getSingleFavoriteUseCase(it)
                _favoriteData.postValue(fav.toUi())
                currentUserUseCase()?.let { user ->
                    _isOnlyViewable.postValue(fav.userEmail != user.email)
                }
            }
        }
    }

    fun createFavorite(emoji: String, title: String) {
        viewModelScope.launch(Dispatchers.IO + otherOperationExceptionHandler) {
            if (favoriteId.isNullOrBlank()) {
                favoriteId = createFavoriteUseCase(
                    Favorite(
                        userEmail = currentUserUseCase()?.email ?: "",
                        title = title,
                        emoji = emoji,
                        movies = emptyList(),
                        id = ""
                    )
                )
            } else {
                favoriteId?.let {
                    updateFavoriteUseCase(it, emoji, title)
                }
            }

            refreshData()
        }

    }
}