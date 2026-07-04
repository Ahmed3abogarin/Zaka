package com.vtol.zaka.presentation.register.signup

sealed class SignUpEvent {
    data class OnNameChanged(val name: String): SignUpEvent()
    data class OnEmailChanged(val email: String): SignUpEvent()
    data class OnPasswordChanged(val password: String): SignUpEvent()
    object ErrorShown: SignUpEvent()
    object OnSignUpClicked: SignUpEvent()
}