package com.vtol.zaka.domain.models.auth

sealed class AuthState {
    object OnBoarding: AuthState()
    object Loading : AuthState()                   // Initial/loading state

    object Unauthenticated : AuthState()          // User not logged in

    object Authenticated : AuthState()  // User logged in

    data class Error(val message: String) : AuthState()
}