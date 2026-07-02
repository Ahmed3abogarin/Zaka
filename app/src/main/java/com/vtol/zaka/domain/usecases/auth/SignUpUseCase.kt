package com.vtol.zaka.domain.usecases.auth

import com.vtol.zaka.domain.models.auth.User
import com.vtol.zaka.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User, password: String) =
        repository.register(user, password)
}