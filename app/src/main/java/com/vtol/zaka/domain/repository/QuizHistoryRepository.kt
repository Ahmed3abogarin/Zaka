package com.vtol.zaka.domain.repository

import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.presentation.quiz.QuizUiState
import kotlinx.coroutines.flow.Flow

interface QuizHistoryRepository {
    suspend fun saveSession(state: QuizUiState)
    fun getAllSessions(): Flow<List<QuizSession>>
    suspend fun getSessionDetail(sessionId: Int): QuizSession?
    suspend fun deleteSession(sessionId: Int)

    suspend fun getSessionWithResults(sessionId: Int): Result<List<Question>>
}