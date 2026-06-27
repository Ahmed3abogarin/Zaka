package com.vtol.zaka.domain.models.quiz

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class QuizResult(
    val title: String,
    val progress: Float,
    val percentage: Int,
    val accentColor: Color,
    val bgColor: Color,
    val iconTint: Color,
)

data class Subject(val label: String, @param:DrawableRes val icon: Int)
