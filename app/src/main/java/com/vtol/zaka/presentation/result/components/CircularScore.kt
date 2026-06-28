package com.vtol.zaka.presentation.result.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.TextPrimary

@Composable
fun CircularScore(correct: Int, total: Int) {
    val percentage = if (total == 0) 0f else correct.toFloat() / total
    val animatedSweep by animateFloatAsState(
        targetValue = percentage * 300f,  // 300° arc
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = "scoreSweep",
    )

    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = 14.dp.toPx()
            val padding = stroke / 2
            val arcSize = Size(size.width - stroke, size.height - stroke)
            val startAngle = 120f   // start bottom-left
            val totalSweep = 300f

            // Track (gray background arc)
            drawArc(
                color = Color(0xFFE5E7EB),
                startAngle = startAngle,
                sweepAngle = totalSweep,
                useCenter = false,
                topLeft = Offset(padding, padding),
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )

            // Progress (purple filled arc)
            drawArc(
                brush = Brush.sweepGradient(
                    listOf(Color(0xFF7C3AED), Color(0xFF5B21B6))
                ),
                startAngle = startAngle,
                sweepAngle = animatedSweep,
                useCenter = false,
                topLeft = Offset(padding, padding),
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )
        }

        // Score text in center
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$correct/$total",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )
        }
    }
}