package com.vtol.zaka.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import com.vtol.zaka.domain.models.RecentQuiz
import com.vtol.zaka.domain.models.quiz.Subject
import com.vtol.zaka.domain.models.quiz.subjects
import com.vtol.zaka.ui.theme.GrayBg
import com.vtol.zaka.ui.theme.GrayCard
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.Orange100
import com.vtol.zaka.ui.theme.Orange400
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.Teal100
import com.vtol.zaka.ui.theme.Teal400
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun HomeScreen(recentQuizzes: List<RecentQuiz>) {
    // Force RTL for Arabic content
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-82).dp, y = (-82).dp)
                    .size(340.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Purple500.copy(alpha = 0.4f), Color.Transparent),
                        ),
                        shape = CircleShape,
                    )
            )


            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-32).dp, y = (-64).dp)
                    .size(340.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFE2F9FE), Color.Transparent),
                        ),
                        shape = CircleShape,
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 82.dp)
                    .size(340.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFEE1FC), Color.Transparent),
                        ),
                        shape = CircleShape,
                    )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp),
            ) {
                item { Spacer(Modifier.height(48.dp)) }

                // ── Greeting ──────────────────────────────────────────────────────
                item { GreetingRow() }
                item { Spacer(Modifier.height(28.dp)) }

                // ── Hero CTA card ─────────────────────────────────────────────────
                item {
                    HeroCard(modifier = Modifier.padding(horizontal = 20.dp))
                }
                item { Spacer(Modifier.height(32.dp)) }

                // ── "Start now" section ───────────────────────────────────────────
                item {
                    SectionTitle(
                        title = "أبدأ الان",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item { Spacer(Modifier.height(14.dp)) }
                item {
                    StartNowRow(modifier = Modifier.padding(horizontal = 20.dp))
                }
                item { Spacer(Modifier.height(32.dp)) }

                // ── Subject filter ────────────────────────────────────────────────
                item {
                    SubjectFilterRow(
                        subjects = subjects,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item { Spacer(Modifier.height(24.dp)) }

                // ── Recent quizzes ────────────────────────────────────────────────
                item {
                    SectionTitle(
                        title = "اختبارات حديثة",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item { Spacer(Modifier.height(14.dp)) }

                items(recentQuizzes.size) { idx ->
                    RecentQuizCard(
                        quiz = recentQuizzes[idx],
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

// ── Greeting row ──────────────────────────────────────────────────────────────
@Composable
private fun GreetingRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "مرحباً، احمد",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )
            Text(
                text = "مستعد لتحدي اليوم؟",
                fontSize = 14.sp,
                color = TextSecond,
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Purple100),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "أ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Purple700,
            )
        }
    }
}

// ── Hero CTA card ─────────────────────────────────────────────────────────────
@Composable
private fun HeroCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(listOf(Purple700, Purple500))
            )
            .clickable {}
            .padding(horizontal = 24.dp, vertical = 28.dp),
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "أنشئ اختبارا جديدا",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "حوّل مذكراتك إلى تحديات ذكية باستخدام الذكاء الاصطناعي",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.End,
                lineHeight = 22.sp,
            )
        }
    }
}

// ── Section title ─────────────────────────────────────────────────────────────
@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = modifier.fillMaxWidth(),
    )
}

// ── Start-now cards (PDF / Camera) ────────────────────────────────────────────
@Composable
private fun StartNowRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Camera card
        ActionCard(
            label = "صوّر صفحة",
            icon = Icons.Outlined.CameraAlt,
            tint = Teal400,
            modifier = Modifier.weight(1f),
        )
        // PDF card
        ActionCard(
            label = "رفع PDF",
            icon = Icons.Outlined.UploadFile,
            tint = Purple500,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ActionCard(
    label: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(width = 0.3.dp, color = Color(0xFFCBC3D9), shape = RoundedCornerShape(16.dp))
            .background(GrayBg)
            .clickable {}
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(tint.copy(alpha = 0.25f))
                .size(46.dp)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                imageVector = icon,
                contentDescription = null,
                tint = tint,
            )

        }
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
        )
    }
}

// ── Subject filter row ────────────────────────────────────────────────────────
@Composable
private fun SubjectFilterRow(
    subjects: List<Subject>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "أو اختر موضوعاً للبدء فوراً",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )

            Text(
                text = "عرض الكل",
                fontSize = 13.sp,
                color = Purple500,
                fontWeight = FontWeight.Medium,
            )
        }
        Spacer(Modifier.height(14.dp))
        Row (
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
           subjects.forEach { subject ->
                SubjectChip(
                    label = subject.label,
                    icon = subject.icon
                )
            }
        }
    }
}

@Composable
private fun SubjectChip(
    label: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(width = 0.25.dp, color = Color(0xFFCBC3D9), shape = RoundedCornerShape(14.dp))
            .background(Color.White)
            .size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
            )
        }
    }
}

// ── Recent quiz card ──────────────────────────────────────────────────────────
@Composable
private fun RecentQuizCard(
    quiz: RecentQuiz,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = GrayCard)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(quiz.bgColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Science,
                    contentDescription = null,
                    tint = quiz.iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }

            // Title + progress bar on the right
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = quiz.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                    )
                    Text(
                        text = "${quiz.percentage}%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = quiz.accentColor,
                    )

                }

                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { quiz.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = quiz.accentColor,
                    trackColor = GrayCard,
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = null,
                tint = TextSecond,
                modifier = Modifier.size(20.dp),
            )

        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(recentQuizzes = listOf())
    }
}