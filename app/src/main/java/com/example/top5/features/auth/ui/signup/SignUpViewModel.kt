package com.example.top5.features.auth.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.LoginException
import com.example.top5.features.auth.domain.usecase.SignUpUseCase
import com.example.top5.features.auth.domain.usecase.ValidateRegistrationUseCase
import com.example.top5.features.auth.ui.AuthRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateRegistrationUseCase: ValidateRegistrationUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableLiveData<AuthRequestState>()
    val signUpState: LiveData<AuthRequestState> = _signUpState

    private val signUpExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _signUpState.postValue(
            AuthRequestState.Error(exception = Exception(throwable))
        )
    }

    fun signUp(username: String, password: String, confirmPassword: String) {
        try {
            validateRegistrationUseCase(username, password, confirmPassword)
            viewModelScope.launch(Dispatchers.IO + signUpExceptionHandler) {
                _signUpState.postValue(AuthRequestState.Loading)

                signUpUseCase(username, password)
                _signUpState.postValue(AuthRequestState.Success)
            }
        } catch (e: BaseAuthException) {
            _signUpState.postValue(AuthRequestState.Error(e))
        }
    }

}