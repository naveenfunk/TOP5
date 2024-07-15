package com.example.top5.features.favorites.ui.viewableProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.favorites.domain.usecase.GetFavoritesByUserEmailUseCase
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewableProfileViewModel @Inject constructor(
    private val getFavoritesByUserEmailUseCase: GetFavoritesByUserEmailUseCase
) : ViewModel() {

    private val _favoritesList = MutableLiveData<List<FavoriteListItem>>(emptyList())
    val favoritesList: LiveData<List<FavoriteListItem>> = _favoritesList

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _favoritesList.postValue(emptyList())
    }

    fun findByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val favorites = getFavoritesByUserEmailUseCase(email)
            _favoritesList.postValue(favorites)
        }
    }

}