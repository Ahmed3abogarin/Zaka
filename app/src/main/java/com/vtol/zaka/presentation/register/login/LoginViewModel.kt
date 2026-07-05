package com.vtol.zaka.presentation.register.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.usecases.auth.LoginUseCase
import com.vtol.zaka.domain.usecases.auth.ResetPasswordUseCase
import com.vtol.zaka.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.value,
                        emailError = ValidationUtils.validateEmail(event.value)
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.value,
                        passwordError = ValidationUtils.validatePassword(event.value)
                    )
                }
            }

            is LoginEvent.ErrorShown -> {
                _uiState.update { it.copy(error = null) }
            }

            is LoginEvent.RestPasswordClicked -> restPassword()
            is LoginEvent.ClearForgotPasswordState -> clearForgotPasswordState()

            is LoginEvent.LoginClicked -> login()

        }
    }

    private fun restPassword() {
        viewModelScope.launch {
            val emailError = ValidationUtils.validateEmail(_uiState.value.email)

            if (emailError != null) {
                _uiState.update {
                    it.copy(emailError = emailError)
                }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, error = null) }


            resetPasswordUseCase(_uiState.value.email.trim())
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, forgetPasswordSuccess = true)
                    }

                }.onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, error = e.message)
                    }
                }
        }

    }

    private fun clearForgotPasswordState() {
        _uiState.update {
            it.copy(
                forgetPasswordSuccess = false,
                forgotPasswordError = null
            )
        }
    }

    private fun login() = viewModelScope.launch {

        val emailError = ValidationUtils.validateEmail(_uiState.value.email)
        val passwordError = ValidationUtils.validatePassword(_uiState.value.password)

        if (emailError != null || passwordError != null) {
            _uiState.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return@launch
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        val result = withContext(Dispatchers.IO) {
            loginUseCase(_uiState.value.email.trim(), _uiState.value.password.trim())
        }

        result
            .onSuccess {
                _uiState.update { it.copy(error = "Welcome back") }
            }
            .onFailure { throwable ->
                _uiState.update { it.copy(isLoading = false, error = throwable.message) }
            }
    }


}


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val forgetPasswordSuccess: Boolean = false,
    val forgotPasswordError: String? = null
)