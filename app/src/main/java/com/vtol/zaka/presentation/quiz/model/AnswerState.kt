package com.vtol.zaka.presentation.quiz.model

import androidx.compose.ui.graphics.Color
import com.vtol.zaka.ui.theme.BorderDefault
import com.vtol.zaka.ui.theme.GreenLight
import com.vtol.zaka.ui.theme.GreenSuccess
import com.vtol.zaka.ui.theme.Purple50
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.RedError
import com.vtol.zaka.ui.theme.RedLight
import com.vtol.zaka.ui.theme.TextPrimary

enum class AnswerState { IDLE, SELECTED, CORRECT, WRONG, REVEALED_CORRECT }

data class QuizOption(
    val text: String,
    val state: AnswerState = AnswerState.IDLE,
)

data class AnswerColors(
    val background: Color,
    val border: Color,
    val text: Color,
    val accent: Color,
)

fun answerColors(state: AnswerState) = when (state) {
    AnswerState.IDLE             -> AnswerColors(Color.White,  BorderDefault, TextPrimary,  Purple500)
    AnswerState.SELECTED         -> AnswerColors(Purple50,     Purple500,     Purple500,    Purple500)
    AnswerState.CORRECT          -> AnswerColors(GreenLight,   GreenSuccess,  GreenSuccess, GreenSuccess)
    AnswerState.WRONG            -> AnswerColors(RedLight,     RedError,      RedError,     RedError)
    AnswerState.REVEALED_CORRECT -> AnswerColors(GreenLight,   GreenSuccess,  GreenSuccess, GreenSuccess)
}