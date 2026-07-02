package com.vtol.zaka.domain.repository

import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.domain.models.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun authState(): Flow<AuthState>
    suspend fun register(user: User, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<Unit>

    suspend fun resetPassword(email: String): Result<Unit>

    fun logout()
}