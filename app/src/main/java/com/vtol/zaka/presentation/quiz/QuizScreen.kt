package com.vtol.zaka.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onClose: () -> Unit = {},
) {

    val state by viewModel.state.collectAsState()

    when (state.screenState) {
        is QuizScreenState.Loading -> {
            QuizLoadingContent(state = state, onReadyToNavigate = {
                viewModel.onQuizReady()
            })
        }

        is QuizScreenState.Ready -> {
            QuizContent(state, event = viewModel::onEvent) { onClose() }
        }

        is QuizScreenState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "حدث خطأ في الحصة (Quota):", color = Color.Red)
                Text(
                    text = (state.screenState as QuizScreenState.Error).message,
                    modifier = Modifier.padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Button(onClick = { viewModel.resetQuiz() }) {
                    Text("إعادة المحاولة")
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
    }
}