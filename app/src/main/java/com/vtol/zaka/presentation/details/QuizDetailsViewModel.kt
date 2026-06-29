package com.vtol.zaka.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.usecases.GetSessionDetail
import com.vtol.zaka.presentation.graph.QuizDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val getSessionDetail: GetSessionDetail,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val sessionId = savedStateHandle.toRoute<QuizDetailsRoute>().sessionId

    private val _state = MutableStateFlow<HistoryDetailUiState>(HistoryDetailUiState.Loading)
    val state = _state.asStateFlow()

    init {
        loadSession()
    }

    private fun loadSession() {
        viewModelScope.launch {
            val session = getSessionDetail(sessionId)
            _state.update {
                if (session != null) HistoryDetailUiState.Success(session)
                else HistoryDetailUiState.Error("لم يتم العثور على الاختبار")
            }
        }
    }
}

sealed interface HistoryDetailUiState {
    data object Loading : HistoryDetailUiState
    data class Success(val session: QuizSession) : HistoryDetailUiState
    data class Error(val message: String) : HistoryDetailUiState
}

sealed interface HistoryDetailEffect {
    data object NavigateToQuiz : HistoryDetailEffect
}