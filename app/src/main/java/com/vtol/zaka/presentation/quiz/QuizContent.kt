package com.vtol.zaka.presentation.quiz

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.quiz.components.AnswerOptionCard
import com.vtol.zaka.presentation.quiz.components.NextButton
import com.vtol.zaka.presentation.quiz.components.QuestionBadge
import com.vtol.zaka.presentation.quiz.components.QuizTopBar
import com.vtol.zaka.presentation.quiz.model.AnswerState
import com.vtol.zaka.presentation.quiz.model.QuizOption
import com.vtol.zaka.ui.theme.BorderDefault
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.TextPrimary

@Composable
fun QuizContent(
    state: QuizUiState,
    event: (QuizEvent) -> Unit,
    onClose: () -> Unit
) {
    val currentQuestion = state.currentQuestion
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        if (currentQuestion == null) {
            LaunchedEffect(currentQuestion) {
                Log.d("QuizQuestions", "current is null")

            }
            // show loading or empty state
            return@CompositionLocalProvider
        }

        val correctIndex = currentQuestion.correctIndex  // ← comes from Question model

        val options = currentQuestion.options.mapIndexed { index, text ->
            when {
                // Before reveal: only show SELECTED, everything else IDLE
                !state.isRevealed -> when {
                    index == state.selectedIndex -> QuizOption(text, AnswerState.SELECTED)
                    else -> QuizOption(text, AnswerState.IDLE)
                }
                // After reveal: show full result
                index == correctIndex && state.selectedIndex == correctIndex ->
                    QuizOption(text, AnswerState.CORRECT)

                index == correctIndex ->
                    QuizOption(text, AnswerState.REVEALED_CORRECT)

                index == state.selectedIndex ->
                    QuizOption(text, AnswerState.WRONG)

                else ->
                    QuizOption(text, AnswerState.IDLE)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF0EEFF), Color.White),
                        endY = 600f,
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                // ── Top bar ───────────────────────────────────────────────────
                QuizTopBar(
                    timerText = state.formattedTime,
                    onClose = onClose,
                )

                // ── Progress bar ──────────────────────────────────────────────
                LinearProgressIndicator(
                    progress = { (state.currentQuestionIndex + 1).toFloat() / state.questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = Purple500,
                    trackColor = BorderDefault,
                )

                // ── Scrollable content ────────────────────────────────────────
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                ) {
                    Spacer(Modifier.height(20.dp))

                    // Question badge
                    QuestionBadge(
                        current = state.currentQuestionIndex + 1,
                        total = state.questions.size,
                    )

                    Spacer(Modifier.height(16.dp))

                    // Question text
                    Text(
                        text = currentQuestion.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        lineHeight = 32.sp,
                        textAlign = TextAlign.Right,
                    )

                    Spacer(Modifier.height(32.dp))

                    // Answer options
                    options.forEachIndexed { index, option ->
                        AnswerOptionCard(
                            option = option,
                            onClick = { event(QuizEvent.SelectAnswer(index)) },
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }

                // Next / Finish button
                NextButton(
                    label = when {
                        !state.isRevealed -> "تحقق من الإجابة"   // Check answer
                        state.isLastQuestion -> "إنهاء الاختبار"    // End test
                        else -> "السؤال التالي"     // Next question
                    },
                    enabled = state.answered,
                    onClick = {
                        when {
                            !state.isRevealed -> event(QuizEvent.RevealAnswer)
                            state.isLastQuestion -> {
                                event(QuizEvent.StopTimer)
                            }

                            else -> event(QuizEvent.NextQuestion)
                        }
                    },
                )
            }
        }
    }
}