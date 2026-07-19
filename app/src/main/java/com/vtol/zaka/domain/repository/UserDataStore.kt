package com.vtol.zaka.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
}