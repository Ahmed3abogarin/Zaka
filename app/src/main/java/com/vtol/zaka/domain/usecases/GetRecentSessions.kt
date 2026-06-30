package com.vtol.zaka.domain.usecases

import com.vtol.zaka.domain.repository.QuizHistoryRepository
import javax.inject.Inject

class GetRecentSessions @Inject constructor(
    private val repository: QuizHistoryRepository
) {
    operator fun invoke() =
        repository.getRecentSession()
}