package com.vtol.zaka.presentation.quiz.model

import com.vtol.zaka.domain.models.quiz.Question

data class QuestionResult(
    val question: Question,
    val selectedIndex: Int,
    val isCorrect: Boolean,
)