package com.vtol.zaka.data.repository

import android.graphics.Bitmap
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content
import com.vtol.zaka.domain.models.quiz.Difficulty
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val model: GenerativeModel
) : QuizRepository {

    override suspend fun generateFromImage(
        bitmap: Bitmap,
        count: Int
    ): Result<List<Question>> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildMaterialPrompt(count)
            val response = model.generateContent(
                content {
                    image(bitmap)  // send image directly
                    text(prompt)
                }
            )
            val json =
                response.text ?: return@withContext Result.failure(Exception("استجابة فارغة"))
            Result.success(parseResponse(json, "مخصص"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ─── Generate from PDF ────────────────────────────────────────────────
    override suspend fun generateFromPdf(
        pdfBytes: ByteArray,
        count: Int
    ): Result<List<Question>> = withContext(Dispatchers.IO) {
        try {
            val mimeType = "application/pdf"
            val prompt = buildMaterialPrompt(count)
            val response = model.generateContent(
                content {
                    inlineData(pdfBytes, mimeType)  // send PDF directly
                    text(prompt)
                }
            )
            val json =
                response.text ?: return@withContext Result.failure(Exception("استجابة فارغة"))
            Result.success(parseResponse(json, "مخصص"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseResponse(raw: String, topic: String): List<Question> {
        val cleaned = raw
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        val root = org.json.JSONObject(cleaned)
        val array = root.getJSONArray("questions")
        val questions = mutableListOf<Question>()

        for (i in 0 until array.length()) {
            val q = array.getJSONObject(i)
            val opts = q.getJSONArray("options")
            val options = (0 until opts.length()).map { opts.getString(it) }

            val difficulty = try {
                Difficulty.valueOf(q.getString("difficulty"))
            } catch (_: Exception) {
                Difficulty.MEDIUM
            }

            questions.add(
                Question(
                    id = q.optString("id", "$topic-$i"),
                    text = q.getString("text"),
                    options = options,
                    correctIndex = options.indexOf(q.getString("correctAnswer")), // ← derive here
                    explanation = q.getString("explanation"),
                    topic = q.optString("topic", topic),
                    difficulty = difficulty
                )
            )
        }
        return questions
    }

    private fun buildMaterialPrompt(count: Int): String = """
        أنت مساعد تعليمي. بناءً على المحتوى المرفق (صورة أو PDF)،
        قم بإنشاء $count أسئلة اختيار من متعدد باللغة العربية
        مستندة فقط إلى المحتوى الموجود.
        
        ${commonRules(count, "مخصص")}
    """.trimIndent()

    private fun commonRules(count: Int, topic: String): String = """
        أعد النتيجة بصيغة JSON فقط بدون أي نص إضافي:
        {
          "questions": [
            {
              "id": "$topic-0",
              "text": "نص السؤال",
              "options": ["الخيار أ", "الخيار ب", "الخيار ج", "الخيار د"],
              "correctAnswer": "يجب أن يكون مطابقاً تماماً لأحد الخيارات",
              "explanation": "تفسير مختصر",
              "topic": "$topic",
              "difficulty": "EASY أو MEDIUM أو HARD"
            }
          ]
        }
        
        قواعد مهمة:
        - JSON فقط بدون ``` أو أي نص قبله أو بعده
        - correctAnswer مطابق تماماً لأحد الخيارات
        - difficulty إحدى هذه القيم: EASY أو MEDIUM أو HARD
        - $count أسئلة بالضبط
    """.trimIndent()
}