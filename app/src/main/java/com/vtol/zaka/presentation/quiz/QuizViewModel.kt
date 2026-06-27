package com.vtol.zaka.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.usecases.quiz.GenerateFromPdfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val generateFromPdfUseCase: GenerateFromPdfUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QuizUiState())
    val state = _state.asStateFlow()


    private val _uiEffect = Channel<QuizUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun generateFromPdf(pdf: ByteArray) {
        _state.update { it.copy(isLoading = true) }
        Log.d("QuizQuestions", "Loading !!!")

        viewModelScope.launch {
            generateFromPdfUseCase(pdf)
                .fold(
                    onSuccess = { questions ->
                        Log.d("QuizQuestions", "Navigating to Quiz")
                        Log.d("QuizQuestions", questions.size.toString())

                        _state.update { it.copy(questions = questions) }
                        _state.update { it.copy(isLoading = false) }

                        _uiEffect.trySend(QuizUiEffect.NavigateToQuiz)
                    },
                    onFailure = { e ->
                        _state.update { it.copy(error = e.message) }
                        _state.update { it.copy(isLoading = false) }
                        Log.d("QuizQuestions", e.message ?: "Error happen")

                    }
                )
        }
    }

    fun selectAnswer(index: Int) {
        if (_state.value.isRevealed) return
        _state.update {
            // tap same option again → deselect
            it.copy(selectedIndex = if (it.selectedIndex == index) null else index)
        }
    }

    fun revealAnswer() {
        if (!_state.value.answered || _state.value.isRevealed) return
        _state.update { it.copy(isRevealed = true) }
    }

    fun nextQuestion() {
        _state.update {
            it.copy(
                currentQuestionIndex = it.currentQuestionIndex + 1,
                selectedIndex = null,
                isRevealed = false,   // ← reset for next question
            )
        }
    }
}

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedIndex: Int? = null,
    val isLoading: Boolean = false,
    val isRevealed: Boolean = false,
    val error: String? = null,
) {
    val answered get() = selectedIndex != null
    val currentQuestion get() = questions.getOrNull(currentQuestionIndex)
    val isLastQuestion get() = currentQuestionIndex == questions.lastIndex
}