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
    val scanType: String,
    val sourceFileName: String,   // display name: "ملخص الكيمياء.pdf"
    val storedFilePath: String,   // ← actual saved file: "1719999999_ملخص الكيمياء.pdf"
    val takenAt: Long = System.currentTimeMillis(),
)