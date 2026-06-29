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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.vtol.zaka.presentation.progress.components.ProgressStatCard
import com.vtol.zaka.presentation.progress.components.StreakCard
import com.vtol.zaka.presentation.progress.components.WeeklyActivityCard
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.util.formatArabicNumber
@Composable
fun ProgressScreen(
    stats: ProgressStats?
) {

    if (stats == null) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator(color = Purple500)
        }
        return
    }


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F9)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item { Spacer(Modifier.height(32.dp)) }

            // ── Streak card ───────────────────────────────────────────────
            item { StreakCard(days = stats.streakDays) }

            // ── Stats 2×2 grid ────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        ProgressStatCard(
                            icon = Icons.Outlined.TrackChanges,
                            iconTint = Color(0xFF2DD4BF),
                            iconBg = Color(0xFFCCFBF1),
                            value = "${stats.overallAccuracy}%",
                            label = "دقة الإجابات",
                            modifier = Modifier.weight(1f),
                        )
                        ProgressStatCard(
                            icon = Icons.AutoMirrored.Outlined.HelpOutline,
                            iconTint = Color(0xFF6366F1),
                            iconBg = Color(0xFFE0E7FF),
                            value = stats.totalQuestionsAnswered.formatArabicNumber(),
                            label = "إجمالي الأسئلة",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        ProgressStatCard(
                            icon = Icons.AutoMirrored.Outlined.MenuBook,
                            iconTint = Color(0xFF64748B),
                            iconBg = Color(0xFFF1F5F9),
                            value = stats.totalSubjects.toString(),
                            label = "مواد دراسية",
                            modifier = Modifier.weight(1f),
                        )
                        ProgressStatCard(
                            icon = Icons.Outlined.CheckBox,
                            iconTint = Color(0xFFF59E0B),
                            iconBg = Color(0xFFFEF3C7),
                            value = stats.totalQuizzesTaken.toString(),
                            label = "اختباراً مكتملاً",
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Weekly activity chart ─────────────────────────────────────
            item { WeeklyActivityCard(activity = stats.weeklyActivity) }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProgressScreenPreview() {
    MaterialTheme {
        ProgressScreen(stats = null)
    }
}