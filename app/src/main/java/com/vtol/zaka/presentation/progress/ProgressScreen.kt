package com.vtol.zaka.presentation.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.vtol.zaka.presentation.progress.components.ProgressStatCard
import com.vtol.zaka.presentation.progress.components.StreakCard
import com.vtol.zaka.presentation.progress.components.WeeklyActivityCard
import com.vtol.zaka.util.formatArabicNumber

data class WeeklyActivity(
    val day: String,
    val count: Int,       // number of quizzes taken that day
)

@Composable
fun ProgressScreen(
    // Replace with: viewModel: ProgressViewModel = hiltViewModel()
    streakDays: Int               = 7,
    accuracy: Int                 = 88,
    totalQuestions: Int           = 1250,
    totalSubjects: Int            = 8,
    totalQuizzes: Int             = 42,
    weeklyActivity: List<WeeklyActivity> = listOf(
        WeeklyActivity("أحد",    2),
        WeeklyActivity("اثن",    3),
        WeeklyActivity("ثلاث",   1),
        WeeklyActivity("أربع",   7),
        WeeklyActivity("خمس",    4),
        WeeklyActivity("جمعة",   3),
        WeeklyActivity("سبت",    2),
    ),
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F9)),
            contentPadding      = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item { Spacer(Modifier.height(32.dp)) }

            // ── Streak card ───────────────────────────────────────────────
            item { StreakCard(days = streakDays) }

            // ── Stats 2×2 grid ────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        ProgressStatCard(
                            icon      = Icons.Outlined.TrackChanges,
                            iconTint  = Color(0xFF2DD4BF),
                            iconBg    = Color(0xFFCCFBF1),
                            value     = "$accuracy%",
                            label     = "دقة الإجابات",
                            modifier  = Modifier.weight(1f),
                        )
                        ProgressStatCard(
                            icon      = Icons.AutoMirrored.Outlined.HelpOutline,
                            iconTint  = Color(0xFF6366F1),
                            iconBg    = Color(0xFFE0E7FF),
                            value     = totalQuestions.formatArabicNumber(),
                            label     = "إجمالي الأسئلة",
                            modifier  = Modifier.weight(1f),
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        ProgressStatCard(
                            icon      = Icons.AutoMirrored.Outlined.MenuBook,
                            iconTint  = Color(0xFF64748B),
                            iconBg    = Color(0xFFF1F5F9),
                            value     = totalSubjects.toString(),
                            label     = "مواد دراسية",
                            modifier  = Modifier.weight(1f),
                        )
                        ProgressStatCard(
                            icon      = Icons.Outlined.CheckBox,
                            iconTint  = Color(0xFFF59E0B),
                            iconBg    = Color(0xFFFEF3C7),
                            value     = totalQuizzes.toString(),
                            label     = "اختباراً مكتملاً",
                            modifier  = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Weekly activity chart ─────────────────────────────────────
            item { WeeklyActivityCard(activity = weeklyActivity) }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProgressScreenPreview() {
    MaterialTheme {
        ProgressScreen()
    }
}