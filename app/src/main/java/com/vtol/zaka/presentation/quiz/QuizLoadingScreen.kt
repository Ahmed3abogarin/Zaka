package com.vtol.zaka.presentation.quiz

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ads.InterstitialAdManager
import com.vtol.zaka.presentation.quiz.model.LoadingSource
import com.vtol.zaka.presentation.quiz.model.getFunFacts
import com.vtol.zaka.presentation.quiz.model.imageSteps
import com.vtol.zaka.presentation.quiz.model.pdfSteps
import com.vtol.zaka.presentation.quiz.model.topicSteps
import com.vtol.zaka.ui.theme.BorderDefault
import com.vtol.zaka.ui.theme.GrayBg
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.Purple300
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond
import com.vtol.zaka.ui.theme.ZakaTheme
import kotlinx.coroutines.delay

@Composable
fun QuizLoadingContent(
    source: LoadingSource = LoadingSource.PDF,
    topic: String = "",
    interstitialAdManager: InterstitialAdManager,
    state: QuizUiState,
    onReadyToNavigate: () -> Unit,
) {

    val steps = remember(source) {
        when (source) {
            LoadingSource.PDF -> pdfSteps
            LoadingSource.IMAGE -> imageSteps
            LoadingSource.TOPIC -> topicSteps
        }
    }
    val funFacts = remember(topic) { getFunFacts(topic) }

    // ─── Local UI state ───────────────────────────────────────────────────
    var currentStep by remember { mutableIntStateOf(0) }
    var currentFact by remember { mutableIntStateOf(0) }
    var adShown by remember { mutableStateOf(false) }
    var adAboutToShow by remember { mutableStateOf(false) }

    // ─── Animate steps independently of AI ─────────────────────────────
    LaunchedEffect(Unit) {
        steps.forEachIndexed { index, step ->
            if (index > currentStep) currentStep = index
            delay(step.durationMs)
        }
    }

    // ─── Rotate fun facts every 3 seconds ────────────────────────────────
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentFact = (currentFact + 1) % funFacts.size
        }
    }
    val activity = LocalActivity.current

    // ─── Key logic: wait for questions, show notice, then navigate ────────
    LaunchedEffect(state.questions) {
        if (state.questions.isNotEmpty() && !adShown) {
            adShown = true
            currentStep = steps.lastIndex   // complete all checkmarks

            delay(600)                      // let final checkmark animate

            adAboutToShow = true            // show ad notice
            delay(1500)                     // user reads notice for 1.5s

            activity?.let {
                interstitialAdManager.showAdAndWait(activity)

            }
            onReadyToNavigate()
        }
    }

    // ─── Background blob animations ───────────────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "bg_blobs")

    val blob1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "blob1_y",
    )
    val blob2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(5500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "blob2_y",
    )
    val blob3Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "blob3_y",
    )

    // ─── UI ───────────────────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        // ── Background blobs ──────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(340.dp)
                .graphicsLayer {
                    translationX = 82.dp.toPx()
                    translationY = blob1Y.dp.toPx()
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Purple500.copy(alpha = 0.4f), Color.Transparent)
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(340.dp)
                .graphicsLayer {
                    translationX = (-72).dp.toPx()
                    translationY = blob2Y.dp.toPx()
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFE2F9FE), Color.Transparent)
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(340.dp)
                .graphicsLayer { translationX = blob3Y.dp.toPx() }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFFEE1FC), Color.Transparent)
                    ),
                    shape = CircleShape,
                )
        )

        // ── Main content ──────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            Spacer(Modifier.height(28.dp))

            // ── Animated current step ─────────────────────────────────────
            AnimatedContent(
                targetState = steps.getOrNull(currentStep),
                transitionSpec = {
                    slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()
                },
                label = "step_content",
            ) { step ->
                step?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(90.dp),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(90.dp),
                                color = Color(0xFF7F77DD),
                                trackColor = Color(0xFF2A2A4A),
                                strokeWidth = 3.dp,
                            )
                            Text(text = it.icon, fontSize = 32.sp)
                        }
                        Text(
                            text = it.titleAr,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                        )
                        Text(
                            text = it.descriptionAr,
                            fontSize = 12.sp,
                            color = TextSecond,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Step dots ─────────────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                steps.forEachIndexed { index, _ ->
                    val isActive = index == currentStep
                    val isDone = index < currentStep
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 10.dp else 6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                when {
                                    isDone -> Purple700
                                    isActive -> Purple300
                                    else -> Color(0xFF2A2A4A)
                                }
                            )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Step checklist ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(0.5.dp, BorderDefault, RoundedCornerShape(14.dp))
                    .background(GrayBg)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                steps.forEachIndexed { index, step ->
                    val isDone = index < currentStep
                    val isActive = index == currentStep

                    AnimatedVisibility(
                        visible = index <= currentStep,
                        enter = fadeIn() + expandVertically(),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isDone) Purple700 else Color.Transparent),
                                contentAlignment = Alignment.Center,
                            ) {
                                when {
                                    isDone -> Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(4.dp),
                                    )

                                    isActive -> CircularProgressIndicator(
                                        modifier = Modifier.fillMaxSize(),
                                        color = Purple700,
                                        strokeWidth = 2.5.dp,
                                        trackColor = Color.LightGray,
                                    )
                                }
                            }

                            Text(
                                text = step.titleAr,
                                fontSize = 15.sp,
                                color = when {
                                    isDone -> Green500
                                    isActive -> TextPrimary
                                    else -> TextSecond
                                },
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp),
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Fun fact card ─────────────────────────────────────────────
            AnimatedContent(
                targetState = funFacts.getOrNull(currentFact),
                transitionSpec = {
                    fadeIn(tween(600)) togetherWith fadeOut(tween(600))
                },
                label = "fun_fact",
            ) { fact ->
                fact?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(0.5.dp, BorderDefault, RoundedCornerShape(12.dp))
                            .background(GrayBg)
                            .padding(14.dp),
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "💡 هل تعلم؟",
                                fontSize = 16.sp,
                                color = Purple700,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                            )
                            Text(
                                text = it,
                                fontSize = 11.sp,
                                color = TextSecond,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth(),
                                lineHeight = 18.sp,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Status message ────────────────────────────────────────────
            AnimatedContent(
                targetState = state.questions.isNotEmpty(),
                label = "status_message",
            ) { isReady ->
                Text(
                    text = if (isReady) "اختبارك جاهز! سيبدأ بعد لحظة ⚡"
                    else "جاري تحضير اختبارك...",
                    fontSize = 11.sp,
                    color = if (isReady) Color(0xFF5DCAA5)
                    else Color.White.copy(alpha = 0.3f),
                    fontWeight = if (isReady) FontWeight.Medium else FontWeight.Normal,
                )
            }

            Spacer(Modifier.height(8.dp))

            // ── Ad notice ─────────────────────────────────────────────────
            AnimatedVisibility(
                visible = adAboutToShow,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 2 },
                exit = fadeOut(tween(200)),
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF3F4F6))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("📢", fontSize = 14.sp)
                    Text(
                        text = "سيظهر إعلان قصير لدعم التطبيق المجاني",
                        fontSize = 12.sp,
                        color = TextSecond,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    ZakaTheme {
        QuizLoadingContent(state = QuizUiState(), interstitialAdManager = InterstitialAdManager(
            LocalActivity.current!!
        )
        ) { }
    }
}