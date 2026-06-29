package com.vtol.zaka.data.repository

import com.vtol.zaka.data.local.dao.QuizSessionDao
import com.vtol.zaka.data.local.entity.QuestionResultEntity
import com.vtol.zaka.data.local.entity.QuizSessionEntity
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.models.quiz.Difficulty
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.repository.QuizHistoryRepository
import com.vtol.zaka.presentation.quiz.QuizUiState
import com.vtol.zaka.presentation.quiz.model.QuestionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import javax.inject.Inject

class QuizHistoryRepositoryImpl @Inject constructor(
    private val dao: QuizSessionDao,
) : QuizHistoryRepository {

    override suspend fun saveSession(state: QuizUiState) {
        val session = QuizSessionEntity(
            topic = state.currentQuestion?.topic ?: "مخصص",
            totalQuestions = state.questions.size,
            correctCount = state.correctCount,
            elapsedSeconds = state.elapsedSeconds,
        )
        val results = state.questionResults.map {
            QuestionResultEntity(
                sessionId = 0,  // filled in by saveFullSession
                questionText = it.question.text,
                options = JSONArray(it.question.options).toString(),
                correctIndex = it.question.correctIndex,
                selectedIndex = it.selectedIndex,
                isCorrect = it.isCorrect,
            )
        }
        dao.saveFullSession(session, results)
    }

    override fun getAllSessions(): Flow<List<QuizSession>> =
        dao.getAllSessions().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getSessionDetail(sessionId: Int): QuizSession? {
        val sessionWithResults = dao.getSessionWithResults(sessionId) ?: return null

        val domainResults = sessionWithResults.results.map { entity ->
            QuestionResult(
                question = Question(
                    id = "${sessionWithResults.session.topic}-${entity.id}",
                    text = entity.questionText,
                    options = JSONArray(entity.options)
                        .let { arr -> (0 until arr.length()).map { arr.getString(it) } },
                    correctIndex = entity.correctIndex,
                    explanation = "",
                    topic = sessionWithResults.session.topic,
                    difficulty = Difficulty.MEDIUM,
                ),
                selectedIndex = entity.selectedIndex,
                isCorrect = entity.isCorrect,
            )
        }

        return sessionWithResults.session.toDomain().copy(results = domainResults)
    }

    override suspend fun deleteSession(sessionId: Int) = dao.deleteSession(sessionId)

    override suspend fun getSessionWithResults(sessionId: Int): Result<List<Question>> {
        return dao.getSessionWithResults(sessionId)
            ?.let { session ->
                Result.success(
                    session.results.map { result ->
                        Question(
                            id           = "${session.session.topic}-${result.id}",
                            text         = result.questionText,
                            options      = JSONArray(result.options)
                                .let { arr -> (0 until arr.length()).map { arr.getString(it) } },
                            correctIndex = result.correctIndex,
                            explanation  = "",
                            topic        = session.session.topic,
                            difficulty   = Difficulty.MEDIUM,
                        )
                    }
                )
            } ?: Result.failure(Exception("Session not found"))
    }
}

private fun QuizSessionEntity.toDomain() = QuizSession(
    id = id,
    topic = topic,
    totalQuestions = totalQuestions,
    correctCount = correctCount,
    elapsedSeconds = elapsedSeconds,
    takenAt = takenAt,
)