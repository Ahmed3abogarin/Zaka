package com.vtol.zaka.presentation.home

import android.graphics.Bitmap
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import android.widget.Toast
import com.vtol.zaka.ads.RewardedAdManager
import com.vtol.zaka.data.local.getFileName
import com.vtol.zaka.domain.models.RecentQuiz
import com.vtol.zaka.domain.usecases.QuotaStatus
import com.vtol.zaka.presentation.home.components.ActionCard
import com.vtol.zaka.presentation.home.components.GreetingRow
import com.vtol.zaka.presentation.home.components.HeroCard
import com.vtol.zaka.presentation.home.components.RecentQuizCard
import com.vtol.zaka.presentation.home.components.SectionTitle
import com.vtol.zaka.presentation.home.components.CategoriesRow
import com.vtol.zaka.presentation.quiz.components.QuotaExceededSheet
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700

@Composable
fun HomeScreen(
    state: HomeUiState,
    rewardedAdManager: RewardedAdManager,
    isAdAvailable: Boolean,
    quotaStatus: QuotaStatus,
    recentQuizzes: List<RecentQuiz>,
    navigateToDetails: (Int) -> Unit,
    onPdfSelected: (ByteArray, String) -> Unit,   // ← handle file directly
    onImageCaptured: (Bitmap) -> Unit,
    loadRewardedAd: () -> Unit,
    onUserRewarded: () -> Unit,
) {

    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        loadRewardedAd()
    }

    var showQuotaSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val pdfLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bytes = context.contentResolver.openInputStream(it)?.readBytes() ?: return@let
            val fileName = getFileName(context, it)
            onPdfSelected(bytes, fileName)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { onImageCaptured(it) }
    }



    if (showQuotaSheet) {
        QuotaExceededSheet(
            remaining = quotaStatus.remaining,
            isAdAvailable = isAdAvailable,
            onDismiss = { showQuotaSheet = false },
            onUpgrade = { /* navigate to paywall */ },
            onWatchAd = {
                activity?.let {
                    rewardedAdManager.showAd(
                        activity = activity,
                        onRewarded = {
                            onUserRewarded()
                            showQuotaSheet = false
                            // now trigger the scan they originally wanted
                            pdfLauncher.launch("application/pdf")
                        },
                        onDismissed = {
                            // user closed ad without finishing — don't grant reward
                        },
                    )
                }
            },
        )
    }


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
                        colors = listOf(Purple500.copy(alpha = 0.2f), Color.Transparent),
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
                        colors = listOf(Color(0xB2E2F9FE), Color.Transparent),
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
                        colors = listOf(
                            Color(0xA6FEE1FC),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape,
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp),
        ) {
            item {
                Spacer(
                    Modifier
                        .statusBarsPadding()
                        .height(6.dp)
                )
            }

            // ── Greeting ──────────────────────────────────────────────────────
            item { GreetingRow(state) }
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
                // Camera card
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionCard(
                        label = "صوّر صفحة",
                        description = "التقط صورة من مذكراتك",
                        icon = Icons.Outlined.DocumentScanner,
                        tint = Purple700,
                        enabled = !state.isOffline,
                        onClick = {
                            if (state.isOffline) {
                                Toast.makeText(context, "عذراً، لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة والمحاولة مرة أخرى.", Toast.LENGTH_SHORT).show()
                            } else {
                                if (quotaStatus.canPlay) cameraLauncher.launch(null)
                                else showQuotaSheet = true
                            }
                        }
                    )
                    ActionCard(
                        label = "ارفع PDF",
                        description = "استيراد دفاتر او مذكرات",
                        icon = Icons.Outlined.PictureAsPdf,
                        tint = Purple500,
                        enabled = !state.isOffline,
                        onClick = {
                            if (state.isOffline) {
                                Toast.makeText(context, "عذراً، لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة والمحاولة مرة أخرى.", Toast.LENGTH_SHORT).show()
                            } else {
                                if (quotaStatus.canPlay) pdfLauncher.launch("application/pdf")
                                else showQuotaSheet = true
                            }
                        }
                    )

                }
            }
            item { Spacer(Modifier.height(32.dp)) }

            // ── Subject filter ────────────────────────────────────────────────
            item {
                CategoriesRow(
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
                    modifier = Modifier.padding(horizontal = 20.dp),
                    quiz = recentQuizzes[idx],
                    onClick = {
                        navigateToDetails(it)
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HomeScreen(
                quotaStatus = QuotaStatus(false, 3, 3),
                recentQuizzes = listOf(),
                navigateToDetails = {},
                onPdfSelected = { _, _ -> },
                onImageCaptured = {},
                isAdAvailable = false,
                loadRewardedAd = {

                },
                onUserRewarded = {

                },
                state = HomeUiState(),
                rewardedAdManager = RewardedAdManager(LocalContext.current)
            )


        }
    }
}