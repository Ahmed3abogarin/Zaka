package com.vtol.zaka.domain.usecases.auth

import com.vtol.zaka.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) =
        repository.resetPassword(email)
}