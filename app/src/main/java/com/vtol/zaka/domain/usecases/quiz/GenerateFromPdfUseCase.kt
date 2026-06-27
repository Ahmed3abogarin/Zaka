package com.vtol.zaka.domain.usecases.quiz

import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.repository.QuizRepository
import javax.inject.Inject

class GenerateFromPdfUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(pdfBytes: ByteArray): Result<List<Question>> {


        if (pdfBytes.isEmpty()) {
            return Result.failure(Exception("الملف فارغ أو تالف"))
        }

        // Check PDF file signature (%PDF-)
        if (!pdfBytes.isPdf()) {
            return Result.failure(Exception("الملف ليس PDF صحيحاً"))
        }

        // 10MB limit
        if (pdfBytes.size > 10 * 1024 * 1024) {
            return Result.failure(Exception("حجم الملف كبير جداً، يجب أن يكون أقل من 10MB"))
        }

        // Check if PDF is password protected
        if (pdfBytes.isPasswordProtected()) {
            return Result.failure(Exception("الملف محمي بكلمة مرور، يرجى إزالة الحماية أولاً"))
        }
        return quizRepository.generateFromPdf(pdfBytes)
    }
    // PDF files always start with %PDF-
    private fun ByteArray.isPdf(): Boolean {
        if (size < 5) return false
        return this[0] == 0x25.toByte() &&  // %
                this[1] == 0x50.toByte() &&  // P
                this[2] == 0x44.toByte() &&  // D
                this[3] == 0x46.toByte() &&  // F
                this[4] == 0x2D.toByte()     // -
    }

    // Password-protected PDFs contain /Encrypt in their bytes
    private fun ByteArray.isPasswordProtected(): Boolean {
        val content = String(this, Charsets.ISO_8859_1)
        return content.contains("/Encrypt")
    }
}