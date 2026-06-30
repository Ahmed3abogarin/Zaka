package com.vtol.zaka.presentation.quiz

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.data.local.FileStorageManager
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.models.ScanType
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.domain.usecases.GenerateFromImage
import com.vtol.zaka.domain.usecases.GetRecentSessions
import com.vtol.zaka.domain.usecases.RetakeQuizUseCase
import com.vtol.zaka.domain.usecases.SaveQuizSessionUseCase
import com.vtol.zaka.domain.usecases.quiz.GenerateFromPdfUseCase
import com.vtol.zaka.domain.usecases.quiz.ValidateImageUseCase
import com.vtol.zaka.presentation.quiz.model.QuestionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val generateFromPdfUseCase: GenerateFromPdfUseCase,
    private val generateFromImageUseCase: GenerateFromImage,
    private val validateImageUseCase: ValidateImageUseCase,
    private val saveQuizSessionUseCase: SaveQuizSessionUseCase,
    private val retakeQuizUseCase: RetakeQuizUseCase,
    private val fileStorageManager: FileStorageManager,
    getRecentSessions: GetRecentSessions
) : ViewModel() {

    private val _state = MutableStateFlow(QuizUiState())
    val state = _state.asStateFlow()

    val recentScans: StateFlow<List<QuizSession>> = getRecentSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiEffect = Channel<QuizUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private var timerJob: Job? = null


    fun onEvent(event: QuizEvent) {
        when (event) {
            QuizEvent.NextQuestion -> nextQuestion()
            QuizEvent.RevealAnswer -> revealAnswer()
            is QuizEvent.SelectAnswer -> selectAnswer(event.index)
            QuizEvent.StartTimer -> startTimer()
            QuizEvent.RetakeQuiz -> retakeQuiz()
        }
    }

    fun generateFromPdf(pdf: ByteArray, fileName: String) {
        val storedPath = fileStorageManager.savePdf(pdf, fileName)
        _state.update {
            it.copy(
                scanType = ScanType.PDF,
                sourceFileName = fileName,
                storedFilePath = storedPath,
                screenState = QuizScreenState.Loading,
            )
        }

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

    fun generateFromImageUri(uri: Uri, context: Context) {
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.IO) { uriToBitmap(context, uri) }
            generateFromImage(bitmap)
        }
    }

    fun generateFromImage(bitmap: Bitmap) {
        val error = validateImageUseCase(bitmap)
        if (error != null) {
            _state.update { it.copy(screenState = QuizScreenState.Error(error.messageAr)) }
            return
        }

        val storedPath = fileStorageManager.saveImage(bitmap)
        _state.update {
            it.copy(
                scanType       = ScanType.IMAGE,
                sourceFileName = "صورة ممسوحة",
                storedFilePath = storedPath,
                screenState    = QuizScreenState.Loading,
            )
        }
        viewModelScope.launch {
            _state.update {
                it.copy(screenState = QuizScreenState.Loading)
            }
            generateFromImageUseCase(bitmap)
                .fold(
                    onSuccess = { questions -> _state.update { it.copy(questions = questions) } },
                    onFailure = { e -> _state.update { it.copy(screenState = QuizScreenState.Error(e.message ?: "حدث خطأ غير متوقع")) } }
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
        if (!_state.value.answered || _state.value.isRevealed) return
        val current = _state.value.currentQuestion ?: return
        val selected = _state.value.selectedIndex ?: return
        val isCorrect = selected == current.correctIndex

        _state.update {
            it.copy(
                isRevealed = true,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                questionResults = it.questionResults + QuestionResult(
                    question = current,
                    selectedIndex = selected,
                    isCorrect = isCorrect,
                )
            )
        }
    }

    fun nextQuestion() {
        val state = _state.value
        if (state.isLastQuestion) {
            viewModelScope.launch {
                stopTimer()
                saveQuizSessionUseCase(state)
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

    fun retakeQuiz() {
        val questions = _state.value.questions

        _state.value = QuizUiState(
            screenState = QuizScreenState.Ready,
            questions = questions
        )

    }

    fun retakeFromHistory(sessionId: Int) {
        viewModelScope.launch {
            retakeQuizUseCase(sessionId)
                .fold(
                    onSuccess = { questions ->
                        Log.d("QuizQuestions", "size: ${questions.size}")
                        _state.update {
                            it.copy(questions = questions, screenState = QuizScreenState.Ready)
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

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
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
    val questionResults: List<QuestionResult> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val correctCount: Int = 0,
    val selectedIndex: Int? = null,
    val isRevealed: Boolean = false,
    val elapsedSeconds: Int = 0,
    val scanType: ScanType = ScanType.PDF,
    val sourceFileName: String = "",
    val storedFilePath: String = ""
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