package com.vtol.zaka.domain.usecases.auth

import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthState> = flow {
        repository.authState().collect { state ->
            if (state is AuthState.Authenticated) {
                val validated = repository.validateCurrentUser()
                emit(validated)
            } else {
                emit(state)
            }
        }
    }
}