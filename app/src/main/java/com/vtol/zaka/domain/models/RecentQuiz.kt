package com.vtol.zaka.domain.models

import androidx.compose.ui.graphics.Color


data class RecentQuiz(
    val sessionId: Int,
    val title: String,
    val progress: Float,
    val percentage: Int,
    val accentColor: Color,
    val bgColor: Color,
    val iconTint: Color,
)