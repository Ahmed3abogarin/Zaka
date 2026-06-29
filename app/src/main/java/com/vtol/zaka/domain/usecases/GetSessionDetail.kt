package com.vtol.zaka.domain.usecases

import com.vtol.zaka.domain.repository.QuizHistoryRepository
import javax.inject.Inject

class GetSessionDetail @Inject constructor(
    private val repository: QuizHistoryRepository
) {
    suspend operator fun invoke(sessionId: Int) =
        repository.getSessionDetail(sessionId)
}