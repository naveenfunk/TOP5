package com.example.top5.features.auth.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.R
import com.example.top5.databinding.FragmentLoginBinding
import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.LoginException
import com.example.top5.features.auth.ui.AuthRequestState
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var dataBinding: FragmentLoginBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.login.setOnClick {
            loginViewModel.login(
                dataBinding.username.text.toString(),
                dataBinding.password.text.toString()
            )
        }
        dataBinding.signUp.setOnClick {
            navigator.navigateFromLoginToSignUp()
        }

        toggleLoading(loading = false)
        loginViewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                is AuthRequestState.Success -> {
                    navigator.navigateFromLoginToHome()
                    toggleLoading(loading = false)
                }

                is AuthRequestState.Error -> {
                    toggleLoading(loading = false)
                    showErrorToast(loginState)
                }

                is AuthRequestState.Loading -> toggleLoading(loading = true)
            }
        }
    }

    private fun showErrorToast(errorState: AuthRequestState.Error) {
        when (errorState.exception) {
            is BaseAuthException.EmptyUsernameException -> showToast(getString(R.string.empty_username))
            is BaseAuthException.EmptyPasswordException -> showToast(getString(R.string.empty_password))
            is BaseAuthException.TooShortPasswordException -> showToast(getString(R.string.password_too_small))
            is LoginException.InvalidCredentialsException -> showToast(getString(R.string.invalid_credentials_error))
            is LoginException.InvalidUserException -> showToast(getString(R.string.invalid_user_error))
            else -> showToast(getString(R.string.something_went_wrong))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun toggleLoading(loading: Boolean) {
        dataBinding.loader.visibility = if (loading) View.VISIBLE else View.GONE
        dataBinding.login.visibility = if (loading) View.GONE else View.VISIBLE
        dataBinding.signUp.visibility = if (loading) View.GONE else View.VISIBLE
    }
}