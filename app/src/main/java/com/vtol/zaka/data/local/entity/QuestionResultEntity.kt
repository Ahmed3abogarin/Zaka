package com.vtol.zaka.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_results",
    foreignKeys = [ForeignKey(
        entity = QuizSessionEntity::class,
        parentColumns = ["id"],
        childColumns = ["sessionId"],
        onDelete = ForeignKey.CASCADE,  // delete results when session deleted
    )],
    indices = [Index("sessionId")],
)
data class QuestionResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Int,
    val questionText: String,
    val options: String,          // JSON array stored as string
    val correctIndex: Int,
    val selectedIndex: Int,
    val isCorrect: Boolean,
)