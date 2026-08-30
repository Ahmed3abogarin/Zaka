package com.vtol.zaka.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onClose: () -> Unit = {},
    navigateToResult: () -> Unit,
    navigateUp: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                QuizUiEffect.NavigateToResult -> navigateToResult()
            }
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
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
            val errorMessage = (state.screenState as QuizScreenState.Error).message
            val isOffline = errorMessage.contains("الإنترنت")

            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isOffline) {
                    Text("📡", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                }
                
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = navigateUp,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Text(if (isOffline) "التحقق من الاتصال والمحاولة ثانية" else "إعادة المحاولة")
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