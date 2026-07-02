package com.vtol.zaka.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onClose: () -> Unit = {},
    navigateToResult: () -> Unit,
    navigateUp: () -> Unit
) {

    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                QuizUiEffect.NavigateToResult -> navigateToResult()
            }
        }
    }

    when (state.screenState) {
        is QuizScreenState.Loading -> {
            QuizLoadingContent(
                interstitialAdManager = viewModel.interstitialAdManager,
                state = state,
                onReadyToNavigate = {
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
                Text(
                    text = (state.screenState as QuizScreenState.Error).message,
                    modifier = Modifier.padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Button(
                    onClick = navigateUp
                ) {
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