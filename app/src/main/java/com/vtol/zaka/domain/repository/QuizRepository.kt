package com.vtol.zaka.domain.repository

import android.graphics.Bitmap
import com.vtol.zaka.domain.models.quiz.Question

interface QuizRepository {
    suspend fun generateFromImage(
        bitmap: Bitmap,
        count: Int = 5
    ): Result<List<Question>>

    // Generate questions from a PDF file
    suspend fun generateFromPdf(
        pdfBytes: ByteArray,
        count: Int = 5
    ): Result<List<Question>>
}