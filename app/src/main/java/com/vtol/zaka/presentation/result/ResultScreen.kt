package com.vtol.zaka.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.quiz.QuizUiState
import com.vtol.zaka.presentation.result.components.CircularScore
import com.vtol.zaka.presentation.result.components.QuestionReviewCard
import com.vtol.zaka.presentation.result.components.ResultButtons
import com.vtol.zaka.presentation.result.components.ResultHeader
import com.vtol.zaka.presentation.result.components.StatsRow
import com.vtol.zaka.ui.theme.*

@Composable
fun ResultScreen(
    state: QuizUiState,
    onHome: () -> Unit,
    retakeQuiz: () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF0EEFF), Color.White),
                        endY = 500f,
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // ── Header ────────────────────────────────────────────────
                item {
                    Spacer(Modifier.height(48.dp))
                    ResultHeader(topic = "التاريخ")
                    Spacer(Modifier.height(28.dp))
                }

                // ── Circular score ────────────────────────────────────────
                item {
                    CircularScore(
                        correct = state.correctCount,
                        total = state.questions.size,
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // ── Time ──────────────────────────────────────────────────
                item {
                    Text(
                        text = "الوقت المستغرق  ${state.formattedTime}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                    )
                    Spacer(Modifier.height(24.dp))
                }

                // ── Stats row ─────────────────────────────────────────────
                item {
                    StatsRow(
                        score = state.score,
                        wrong = state.questions.size - state.correctCount,
                        correct = state.correctCount,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(32.dp))
                }

                // ── Review section title ───────────────────────────────────
                item {
                    Text(
                        text = "مراجعة الاسئلة",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // ── Question review cards ──────────────────────────────────
                itemsIndexed(state.questionResults) { index, result ->
                    QuestionReviewCard(
                        index = index + 1,
                        result = result,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(12.dp))
                }

                item { Spacer(Modifier.height(8.dp)) }

                // ── Buttons ───────────────────────────────────────────────
                item {
                    ResultButtons(
                        onRetake = { retakeQuiz() },
                        onHome = onHome,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    MaterialTheme {
        ResultScreen(
            QuizUiState(),
            onHome = {},
            retakeQuiz = {}
        )
    }
}