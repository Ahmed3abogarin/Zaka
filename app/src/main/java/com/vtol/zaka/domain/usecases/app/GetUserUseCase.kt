package com.vtol.zaka.domain.usecases.app

import com.vtol.zaka.domain.repository.AppRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke() =
        repository.getUser()
}