package com.vtol.zaka.domain.repository

import com.vtol.zaka.domain.models.auth.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getUser(): Flow<User>
}