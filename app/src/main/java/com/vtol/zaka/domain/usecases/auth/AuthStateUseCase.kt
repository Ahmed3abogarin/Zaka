package com.vtol.zaka.domain.usecases.auth

import com.vtol.zaka.domain.repository.AuthRepository
import javax.inject.Inject

class AuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() =
        repository.authState()
}