package com.vtol.zaka.presentation.register.login

sealed class LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    object RestPasswordClicked : LoginEvent()
    object LoginClicked : LoginEvent()

    object ErrorShown: LoginEvent()
    object ClearForgotPasswordState: LoginEvent()

//    data class GoogleClicked(val context: Context): LoginEvent()
}