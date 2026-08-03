package com.vtol.zaka.presentation.onboarding.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.TextPrimary
import kotlin.math.roundToInt

@Composable
fun SlideToStartButton(
    text: String,
    onSlideComplete: () -> Unit
) {
    val trackHeight = 64.dp
    val handleSize = 56.dp

    // Detects the app's current layout direction (follows system/locale automatically,
    // so this works for Arabic without any extra setup if your app supports RTL).
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    var trackWidthPx by remember { mutableFloatStateOf(0f) }
    val handleSizePx = with(LocalDensity.current) { handleSize.toPx() }
    val maxDragPx by remember(trackWidthPx) {
        mutableFloatStateOf((trackWidthPx - handleSizePx).coerceAtLeast(0f))
    }

    // "progress" is direction-agnostic: 0 = not dragged, maxDragPx = fully dragged.
    // We convert this to an actual screen-space pixel offset separately below.
    var progress by remember { mutableFloatStateOf(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "handle_progress"
    )

    val dragState = rememberDraggableState { delta ->
        // In LTR, dragging right (positive delta) increases progress.
        // In RTL, dragging left (negative delta) increases progress, so flip the sign.
        val directional = if (isRtl) -delta else delta
        progress = (progress + directional).coerceIn(0f, maxDragPx)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(trackHeight)
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.horizontalGradient(listOf(Purple700, Purple500)))
            .onGloballyPositioned { coordinates ->
                trackWidthPx = coordinates.size.width.toFloat()
            },
        // CenterStart resolves to the left in LTR and the right in RTL automatically,
        // so the handle's resting position mirrors correctly without extra math.
        contentAlignment = Alignment.CenterStart
    ) {
        // Group fade — text + chevrons fade together as the handle covers them
        val contentAlpha = 1f - (animatedProgress / (maxDragPx.coerceAtLeast(1f))).coerceIn(0f, 1f)

        // Text stays as-is. A Row's children are placed start-to-end, and "start"
        // automatically flips for RTL, so this order needs no manual isRtl branching —
        // text sits at the reading start, chevrons trail toward the drag direction.
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.White.copy(alpha = contentAlpha),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Three chevrons trailing away from the text, each progressively more
            // transparent — nearest to the text is most opaque, farthest is faintest.
            Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                val chevronBaseAlphas = listOf(1f, 0.6f, 0.3f).reversed()
                chevronBaseAlphas.forEach { baseAlpha ->
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = baseAlpha * contentAlpha),
                    )
                }
            }
        }

        // Draggable circular handle
        Box(
            modifier = Modifier
                .padding(4.dp)
                .offset {
                    // Pixel-based offset does NOT auto-mirror in RTL, so we negate it
                    // ourselves: moving "forward" visually means moving left in RTL.
                    val x = if (isRtl) -animatedProgress.roundToInt() else animatedProgress.roundToInt()
                    IntOffset(x, 0)
                }
                .size(handleSize)
                .clip(CircleShape)
                .background(Color.White)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = dragState,
                    onDragStopped = {
                        // Threshold: 85% of the way across counts as "completed"
                        if (progress >= maxDragPx * 0.85f && !isCompleted) {
                            isCompleted = true
                            progress = maxDragPx
                            onSlideComplete()
                        } else {
                            progress = 0f
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            // AutoMirrored icon flips its direction automatically based on LayoutDirection
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Slide to start",
                tint = TextPrimary
            )
        }
    }
}