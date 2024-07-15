package com.example.top5.features.favorites.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.auth.domain.usecase.GetCurrentUserUseCase
import com.example.top5.features.auth.domain.usecase.LogoutUseCase
import com.example.top5.features.favorites.domain.usecase.DeleteFavoriteUseCase
import com.example.top5.features.favorites.domain.usecase.GetFavoritesUseCase
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _favoritesList = MutableLiveData<List<FavoriteListItem>>(emptyList())
    val favoritesList: LiveData<List<FavoriteListItem>> = _favoritesList

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _favoritesList.postValue(emptyList())
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            currentUserUseCase()?.let {
                val favorites = getFavoritesUseCase(it.email)
                _favoritesList.postValue(favorites)
            }
        }
    }

    fun logout() {
        logoutUseCase()
    }

    fun deleteFavorite(favorite: FavoriteListItem) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            deleteFavoriteUseCase(favoriteId = favorite.id)
            refreshData()
        }
    }
}