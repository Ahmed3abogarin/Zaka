package com.vtol.zaka.presentation.scan

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.vtol.zaka.presentation.quiz.QuizViewModel

@Composable
fun ScanScreen(
    viewModel: QuizViewModel,
    navigateToViewer: (Int) -> Unit,
    navigateToQuiz: () -> Unit
) {
    val recentSessions by viewModel.recentScans.collectAsState()
    ScanContent(
        recentSessions = recentSessions,
        navigateToViewer = navigateToViewer,
        generateFromPdf = { bytes, name ->
            viewModel.generateFromPdf(bytes, name)
            navigateToQuiz()
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScanScreenPreview() {
    MaterialTheme {
    }
}
