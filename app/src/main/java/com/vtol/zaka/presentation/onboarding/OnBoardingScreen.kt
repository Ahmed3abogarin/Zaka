package com.vtol.zaka.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.ZakaTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.vtol.zaka.R
import com.vtol.zaka.presentation.onboarding.components.SlideToStartButton
import com.vtol.zaka.presentation.onboarding.model.onboardingPages

private const val PAGE_DURATION_MS = 4000
private const val FADE_DURATION_MS = 400

@Composable
fun OnboardingScreen(onSlideComplete: () -> Unit) {
    var currentPage by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Drives the progress bar for the CURRENT page, and advances
    // to the next page (looping back to 0) once it completes.
    LaunchedEffect(currentPage) {
        progress = 0f
        val steps = 60
        val stepDelay = PAGE_DURATION_MS / steps
        for (i in 1..steps) {
            delay(stepDelay.toLong().milliseconds)
            progress = i / steps.toFloat()
        }
        currentPage = (currentPage + 1) % onboardingPages.size
    }

    Box {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.img_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {

            // ---- Segmented progress bar ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                onboardingPages.indices.forEach { index ->
                    val segmentProgress = when {
                        index < currentPage -> 1f
                        index == currentPage -> progress
                        else -> 0f
                    }
                    LinearProgressIndicator(
                        progress = { segmentProgress },
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.25f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ---- Title: fades out old text, fades in new text ----
            AnimatedContent(
                targetState = currentPage,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(
                            FADE_DURATION_MS,
                            easing = LinearEasing
                        )
                    ) togetherWith
                            fadeOut(animationSpec = tween(FADE_DURATION_MS, easing = LinearEasing))
                },
                label = "title_fade"
            ) { pageIndex ->
                Text(
                    text = onboardingPages[pageIndex].title,
                    color = Color.White,
                    fontSize = 32.sp,
                    lineHeight = 38.sp,
                    fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---- Subtitle: same fade treatment ----
            AnimatedContent(
                targetState = currentPage,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = FADE_DURATION_MS,
                            easing = LinearEasing
                        )
                    ) togetherWith
                            fadeOut(animationSpec = tween(FADE_DURATION_MS, easing = LinearEasing))
                },
                label = "subtitle_fade"
            ) { pageIndex ->
                Text(
                    text = onboardingPages[pageIndex].subtitle,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            SlideToStartButton(text = "ابدا الان", onSlideComplete = onSlideComplete)
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ZakaTheme {
            OnboardingScreen {}
        }
    }
}