package com.vtol.zaka.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.models.RecentQuiz
import com.vtol.zaka.domain.models.auth.User
import com.vtol.zaka.domain.usecases.GetAllSessionsUseCase
import com.vtol.zaka.domain.usecases.app.GetUserUseCase
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.Orange100
import com.vtol.zaka.ui.theme.Orange400
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal100
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllSessionsUseCase: GetAllSessionsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()
    val recentQuizzes: StateFlow<List<RecentQuiz>> = getAllSessionsUseCase()
        .map { sessions ->
            sessions.take(3).map { session ->
                RecentQuiz(
                    sessionId = session.id,
                    title = session.topic,
                    progress = session.score / 100f,
                    percentage = session.score,
                    accentColor = colorForScore(session.score),
                    bgColor = bgForScore(session.score),
                    iconTint = colorForScore(session.score),
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        getUser()
    }



    fun getUser() {
        _state.update { it.copy(isUserLoading = true) }
        viewModelScope.launch {
            getUserUseCase()
                .catch { e ->
                    _state.update {
                        it.copy(
                            userError = e.message,
                            isUserLoading = false
                        )
                    }
                }
                .collect { data ->
                    _state.update { it.copy(user = data, isUserLoading = false) }
                }
        }
    }
}

data class HomeUiState(
    val isUserLoading: Boolean = false,
    val userError: String? = null,
    val user: User? = null
)

private fun colorForScore(score: Int) = when {
    score >= 80 -> Green500
    score >= 50 -> Purple500
    else -> Orange400
}

private fun bgForScore(score: Int) = when {
    score >= 80 -> Teal100
    score >= 50 -> Purple100
    else -> Orange100
}