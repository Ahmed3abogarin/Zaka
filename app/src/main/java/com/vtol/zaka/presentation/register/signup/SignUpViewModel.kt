package com.vtol.zaka.presentation.register.signup

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.vtol.zaka.domain.models.auth.User
import com.vtol.zaka.domain.usecases.auth.SignInWithGoogleUseCase
import com.vtol.zaka.domain.usecases.auth.SignUpUseCase
import com.vtol.zaka.util.Constants
import com.vtol.zaka.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()


    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameChanged -> {
                _uiState.update {
                    it.copy(
                        name = event.name,
                        nameError = ValidationUtils.validateName(event.name)
                    )
                }
            }

            is SignUpEvent.OnEmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = ValidationUtils.validateEmail(event.email)
                    )
                }
            }

            is SignUpEvent.OnPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = ValidationUtils.validatePassword(event.password)
                    )
                }
            }

            is SignUpEvent.ErrorShown -> {
                _uiState.update { it.copy(error = null) }
            }

            is SignUpEvent.OnSignUpClicked -> register()

            is SignUpEvent.OnGoogleSignUpClicked -> signInWithGoogle(event.context)
        }
    }

    private fun signInWithGoogle(context: android.content.Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(Constants.WEB_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val result = credentialManager.getCredential(context, request)
                val googleIdTokenCredential =
                    com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(
                        result.credential.data
                    )
                val idToken = googleIdTokenCredential.idToken

                signInWithGoogleUseCase(idToken)
                    .onSuccess {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                    .onFailure { e ->
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }


    fun register() = viewModelScope.launch {

        val nameError = ValidationUtils.validateName(_uiState.value.name)
        val emailError = ValidationUtils.validateEmail(_uiState.value.email)
        val passwordError = ValidationUtils.validatePassword(_uiState.value.password)

        if (nameError != null || emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return@launch
        }
        _uiState.update { it.copy(isLoading = true, error = null) }

        signUpUseCase(
            User(name = _uiState.value.name.trim(), email = _uiState.value.email),
            _uiState.value.password
        ).onFailure { failure ->
                _uiState.update {
                    it.copy(isLoading = false, error = failure.message)
                }
            }
    }
}

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)