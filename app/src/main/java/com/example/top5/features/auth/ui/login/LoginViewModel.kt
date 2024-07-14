package com.example.top5.features.auth.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.LoginException
import com.example.top5.features.auth.domain.usecase.LoginUseCase
import com.example.top5.features.auth.domain.usecase.ValidateCredentialsUseCase
import com.example.top5.features.auth.ui.AuthRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateCredentialsUseCase: ValidateCredentialsUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<AuthRequestState>()
    val loginState: LiveData<AuthRequestState> = _loginState

    private val loginExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loginState.postValue(
            AuthRequestState.Error(exception = Exception(throwable))
        )
    }

    fun login(username: String, password: String) {
        try {
            validateCredentialsUseCase(username, password)
            viewModelScope.launch(Dispatchers.IO + loginExceptionHandler) {
                _loginState.postValue(AuthRequestState.Loading)

                loginUseCase(username, password)
                _loginState.postValue(AuthRequestState.Success)
            }
        } catch (e: BaseAuthException) {
            _loginState.postValue(AuthRequestState.Error(e))
        }
    }


}