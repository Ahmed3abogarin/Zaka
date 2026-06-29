package com.vtol.zaka.presentation.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.usecases.GetProgressStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getProgressStatsUseCase: GetProgressStatsUseCase
) : ViewModel() {

    val stats: StateFlow<ProgressStats?> = getProgressStatsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )
}

data class ProgressStats(
    val totalQuestionsAnswered: Int,
    val overallAccuracy: Int,
    val totalQuizzesTaken: Int,
    val totalSubjects: Int,
    val streakDays: Int,
    val weeklyActivity: List<WeeklyActivity>,
)

data class WeeklyActivity(
    val day: String,
    val count: Int,
)