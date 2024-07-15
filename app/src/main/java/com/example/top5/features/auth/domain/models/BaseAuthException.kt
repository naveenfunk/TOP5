package com.example.top5.features.auth.domain.models


open class BaseAuthException(errorText: String) : Exception(errorText) {
    class EmptyUsernameException(errorText: String = "empty username is not allowed") : BaseAuthException(errorText = errorText)
    class EmptyPasswordException(errorText: String = "empty password is not allowed") : BaseAuthException(errorText = errorText)
    class TooShortPasswordException(errorText: String = "password should be atleast 6 characters long") : BaseAuthException(errorText = errorText)
    class UnknownException(errorText: String = "something went wrong with sign up") : BaseAuthException(errorText = errorText)
}
