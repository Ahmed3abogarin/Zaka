package com.vtol.zaka.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.usecases.quiz.GenerateFromPdfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

    private var timerJob: Job? = null


    fun onEvent(event: QuizEvent){
        when(event){
            QuizEvent.NextQuestion -> nextQuestion()
            QuizEvent.RevealAnswer -> revealAnswer()
            is QuizEvent.SelectAnswer -> selectAnswer(event.index)
            QuizEvent.StopTimer -> stopTimer()
            QuizEvent.StartTimer -> startTimer()
        }
    }

    fun generateFromPdf(pdf: ByteArray) {
        viewModelScope.launch {
            Log.d("QuizQuestions", "Loading")

            // ✅ Set loading state
            _state.update {
                it.copy(screenState = QuizScreenState.Loading)
            }

            generateFromPdfUseCase(pdf)
                .fold(
                    onSuccess = { questions ->
                        Log.d("QuizQuestions", "size: ${questions.size}")
                        _state.update {
                            it.copy(questions = questions)
                        }
                    },
                    onFailure = { e ->
                        Log.d("QuizQuestions", "${e.message}")

                        _state.update {
                            it.copy(
                                screenState = QuizScreenState.Error(
                                    e.message ?: "حدث خطأ غير متوقع"
                                )
                            )
                        }
                    }
                )
        }
    }

    fun selectAnswer(index: Int) {
        if (_state.value.isRevealed) return
        _state.update {
            it.copy(selectedIndex = if (it.selectedIndex == index) null else index)
        }
    }

    fun revealAnswer() {
        if (_state.value.isRevealed) return

        val isCorrect = _state.value.selectedIndex ==
                _state.value.currentQuestion?.correctIndex
        _state.update {
            it.copy(
                isRevealed = true,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount
            )
        }
    }

    fun nextQuestion() {
        val state = _state.value
        if (state.isLastQuestion) {
            viewModelScope.launch {
                stopTimer()
                _uiEffect.trySend(QuizUiEffect.NavigateToResult)
            }
            return
        }
        _state.update {
            it.copy(
                currentQuestionIndex = it.currentQuestionIndex + 1,
                selectedIndex = null,
                isRevealed = false
            )
        }
    }

    fun onQuizReady() {
        viewModelScope.launch {
            _state.update { it.copy(screenState = QuizScreenState.Ready) }
        }
    }


    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _state.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun resetQuiz() {
        stopTimer()
        _state.value = QuizUiState()
    }

    override fun onCleared() {
        timerJob?.cancel()
    }
}

// ─── Screen State — what to SHOW ──────────────────────────────────────────────
sealed class QuizScreenState {
    object Loading : QuizScreenState()          // show loading UI + ad
    object Ready : QuizScreenState()          // show loading UI + ad
    data class Error(val message: String) : QuizScreenState()
}

// ─── Quiz Data State — the actual quiz data ───────────────────────────────────
data class QuizUiState(
    val screenState: QuizScreenState = QuizScreenState.Loading,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val correctCount: Int = 0,
    val selectedIndex: Int? = null,
    val isRevealed: Boolean = false,
    val elapsedSeconds: Int = 0
) {
    val answered get() = selectedIndex != null
    val currentQuestion get() = questions.getOrNull(currentQuestionIndex)
    val isLastQuestion get() = currentQuestionIndex == questions.lastIndex
    val score
        get() = if (questions.isEmpty()) 0
        else (correctCount * 100) / questions.size
    val scorePercentage
        get() = if (questions.isEmpty()) 0f
        else correctCount.toFloat() / questions.size * 100f
    val formattedTime: String
        get() {
            val minutes = elapsedSeconds / 60
            val seconds = elapsedSeconds % 60
            return "%d:%02d".format(minutes, seconds)
        }
}