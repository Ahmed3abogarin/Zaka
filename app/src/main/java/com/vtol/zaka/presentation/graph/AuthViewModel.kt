package com.vtol.zaka.presentation.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.domain.repository.UserDataStore
import com.vtol.zaka.domain.usecases.auth.AuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    authStateUseCase: AuthStateUseCase,
    private val userDataStore: UserDataStore
): ViewModel() {
    val authState: StateFlow<AuthState> =
        combine(
            authStateUseCase(),
            userDataStore.isOnboardingCompleted()
        ) { authState, onboardingCompleted ->
            if (!onboardingCompleted) {
                AuthState.OnBoarding
            } else {
                authState
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AuthState.Loading
        )


    fun completeOnBoarding() {
        viewModelScope.launch {
            userDataStore.setOnboardingCompleted(true)
        }
    }
}