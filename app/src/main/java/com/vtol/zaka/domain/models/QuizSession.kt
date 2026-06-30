package com.vtol.zaka.domain.models

import com.vtol.zaka.presentation.quiz.model.QuestionResult
import com.vtol.zaka.util.toRelativeTimeArabic
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class QuizSession(
    val id: Int,
    val topic: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val elapsedSeconds: Int,
    val scanType: ScanType,
    val sourceFileName: String,
    val sourceFilePath: String,
    val takenAt: Long,
    val results: List<QuestionResult> = emptyList(),
) {
    val score get() = (correctCount * 100) / totalQuestions.coerceAtLeast(1)
    val formattedTime get() = "%d:%02d".format(elapsedSeconds / 60, elapsedSeconds % 60)
    val formattedDate get() = SimpleDateFormat("dd MMM yyyy", Locale("ar")).format(Date(takenAt))

    val relativeTime get() = takenAt.toRelativeTimeArabic()
}

enum class ScanType { PDF, IMAGE }