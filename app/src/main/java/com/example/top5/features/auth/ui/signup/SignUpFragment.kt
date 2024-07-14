package com.example.top5.features.auth.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.R
import com.example.top5.databinding.FragmentSignupBinding
import com.example.top5.features.auth.domain.models.BaseAuthException
import com.example.top5.features.auth.domain.models.SignUpException
import com.example.top5.features.auth.ui.AuthRequestState
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    private lateinit var dataBinding: FragmentSignupBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.login.setOnClick {
            navigator.navigateBack()
        }
        dataBinding.signUp.setOnClick {
            signUpViewModel.signUp(
                dataBinding.username.text.toString(),
                dataBinding.password.text.toString(),
                dataBinding.confirmPassword.text.toString()
            )
        }

        toggleLoading(loading = false)
        signUpViewModel.signUpState.observe(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is AuthRequestState.Success -> {
                    toggleLoading(loading = false)
                    dataBinding.login.performClick()
                    showToast(getString(R.string.sign_up_success_message))
                }

                is AuthRequestState.Error -> {
                    toggleLoading(loading = false)
                    showErrorToast(signUpState)
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
            is SignUpException.MismatchPasswordsException -> showToast(getString(R.string.mismatch_password_exception))
            is SignUpException.WeakPasswordException -> showToast(getString(R.string.weak_password_error))
            is SignUpException.UserAlreadyExistException -> showToast(getString(R.string.user_already_exists))
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