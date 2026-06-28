package com.vtol.zaka.presentation.scan

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vtol.zaka.presentation.quiz.QuizViewModel

@Composable
fun ScanScreen(viewModel: QuizViewModel, navigateToQuiz: () -> Unit) {
    ScanContent {
        viewModel.generateFromPdf(it)
        navigateToQuiz()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScanScreenPreview() {
    MaterialTheme {
    }
}
