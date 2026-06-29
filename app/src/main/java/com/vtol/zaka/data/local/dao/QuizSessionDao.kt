package com.vtol.zaka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.vtol.zaka.data.local.entity.QuestionResultEntity
import com.vtol.zaka.data.local.entity.QuizSessionEntity
import com.vtol.zaka.domain.models.SessionWithResults
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizSessionDao {

    @Insert
    suspend fun insertSession(session: QuizSessionEntity): Long  // returns generated id

    @Insert
    suspend fun insertResults(results: List<QuestionResultEntity>)

    @Transaction
    suspend fun saveFullSession(
        session: QuizSessionEntity,
        results: List<QuestionResultEntity>,
    ) {
        val sessionId = insertSession(session).toInt()
        insertResults(results.map { it.copy(sessionId = sessionId) })
    }

    @Query("SELECT * FROM quiz_sessions ORDER BY takenAt DESC")
    fun getAllSessions(): Flow<List<QuizSessionEntity>>

    @Query("SELECT * FROM question_results WHERE sessionId = :sessionId")
    suspend fun getResultsForSession(sessionId: Int): List<QuestionResultEntity>

    @Query("DELETE FROM quiz_sessions WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: Int)

    @Transaction
    @Query("SELECT * FROM quiz_sessions WHERE id = :sessionId")
    suspend fun getSessionWithResults(sessionId: Int): SessionWithResults?
}