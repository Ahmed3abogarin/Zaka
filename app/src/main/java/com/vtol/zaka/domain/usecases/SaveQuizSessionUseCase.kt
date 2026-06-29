package com.vtol.zaka.domain.usecases

import com.vtol.zaka.domain.repository.QuizHistoryRepository
import com.vtol.zaka.presentation.quiz.QuizUiState
import javax.inject.Inject

class SaveQuizSessionUseCase @Inject constructor(
    private val repository: QuizHistoryRepository,
) {
    suspend operator fun invoke(state: QuizUiState) = repository.saveSession(state)
}