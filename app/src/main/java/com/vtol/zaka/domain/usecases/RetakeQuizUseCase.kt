package com.vtol.zaka.domain.usecases

import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.repository.QuizHistoryRepository
import javax.inject.Inject

class RetakeQuizUseCase @Inject constructor(
    private val repository: QuizHistoryRepository,
) {
    suspend operator fun invoke(sessionId: Int): Result<List<Question>> =
        repository.getSessionWithResults(sessionId)
}