package com.vtol.zaka.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_sessions")
data class QuizSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val topic: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val elapsedSeconds: Int,
    val takenAt: Long = System.currentTimeMillis(),
)