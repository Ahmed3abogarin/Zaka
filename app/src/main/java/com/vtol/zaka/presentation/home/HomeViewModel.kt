package com.vtol.zaka.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.models.RecentQuiz
import com.vtol.zaka.domain.usecases.GetAllSessionsUseCase
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.Orange100
import com.vtol.zaka.ui.theme.Orange400
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal100
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllSessionsUseCase: GetAllSessionsUseCase
) : ViewModel() {

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
}

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