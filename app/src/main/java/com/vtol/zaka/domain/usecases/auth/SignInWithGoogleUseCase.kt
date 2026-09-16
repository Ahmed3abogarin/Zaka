package com.vtol.zaka.domain.usecases.auth

import com.vtol.zaka.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) =
        repository.signInWithGoogle(idToken)
}
