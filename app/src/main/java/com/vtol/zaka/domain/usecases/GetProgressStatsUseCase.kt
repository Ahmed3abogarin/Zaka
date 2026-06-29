package com.vtol.zaka.domain.usecases

import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.repository.QuizHistoryRepository
import com.vtol.zaka.presentation.progress.ProgressStats
import com.vtol.zaka.presentation.progress.WeeklyActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class GetProgressStatsUseCase @Inject constructor(
    private val repository: QuizHistoryRepository,
) {
    operator fun invoke(): Flow<ProgressStats> =
        repository.getAllSessions().map { sessions ->
            if (sessions.isEmpty()) return@map emptyStats()

            val totalQuestions = sessions.sumOf { it.totalQuestions }
            val totalCorrect = sessions.sumOf { it.correctCount }

            ProgressStats(
                totalQuestionsAnswered = totalQuestions,
                overallAccuracy = (totalCorrect * 100) / totalQuestions.coerceAtLeast(1),
                totalQuizzesTaken = sessions.size,
                totalSubjects = sessions.map { it.topic }.toSet().size,
                streakDays = calculateStreak(sessions),
                weeklyActivity = calculateWeeklyActivity(sessions),
            )
        }

    private fun emptyStats() = ProgressStats(
        totalQuestionsAnswered = 0,
        overallAccuracy = 0,
        totalQuizzesTaken = 0,
        totalSubjects = 0,
        streakDays = 0,
        weeklyActivity = emptyWeek(),
    )

    // ── Streak: count consecutive days going back from today ─────────────
    private fun calculateStreak(sessions: List<QuizSession>): Int {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val activeDays = sessions
            .map { it.takenAt.toStartOfDay() }
            .toSortedSet(compareByDescending { it })

        var streak = 0
        var current = today

        while (activeDays.contains(current)) {
            streak++
            current -= 86_400_000L   // minus one day in ms
        }
        return streak
    }

    // ── Weekly activity: sessions per day for the past 7 days ────────────
    private fun calculateWeeklyActivity(sessions: List<QuizSession>): List<WeeklyActivity> {
        val dayNames = listOf("أحد", "اثن", "ثلاث", "أربع", "خمس", "جمعة", "سبت")

        // Build map: dayOfWeek (1=Sun..7=Sat) → count
        val countByDay = sessions.groupBy { session ->
            Calendar.getInstance()
                .apply { timeInMillis = session.takenAt }
                .get(Calendar.DAY_OF_WEEK)
        }.mapValues { it.value.size }

        // Return all 7 days in order starting from Sunday
        return (1..7).map { dayIndex ->
            WeeklyActivity(
                day = dayNames[dayIndex - 1],
                count = countByDay[dayIndex] ?: 0,
            )
        }
    }

//    private fun calculateSubjectBreakdown(sessions: List<QuizSession>): List<SubjectStat> =
//        sessions.groupBy { it.topic }.map { (topic, list) ->
//            SubjectStat(
//                topic     = topic,
//                quizCount = list.size,
//                accuracy  = (list.sumOf { it.correctCount } * 100) /
//                        list.sumOf { it.totalQuestions }.coerceAtLeast(1),
//            )
//        }.sortedByDescending { it.quizCount }

    private fun Long.toStartOfDay(): Long =
        Calendar.getInstance().apply {
            timeInMillis = this@toStartOfDay
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    private fun emptyWeek() = listOf(
        "أحد", "اثن", "ثلاث", "أربع", "خمس", "جمعة", "سبت"
    ).map { WeeklyActivity(it, 0) }
}