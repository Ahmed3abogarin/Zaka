package com.vtol.zaka.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.vtol.zaka.ui.theme.Purple700

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode

@Composable
fun rememberShiningBrush(
    colors: List<Color> = listOf(
        Color(0xFFEEDBFF),
        Purple700,
        Color(0xFFEEDBFF)
    )
): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")

    // Don't use `by` here — keep the State object itself, don't read .value in composition
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnim"
    )

    // remember() so the Brush object itself isn't recreated each frame
    return remember(colors) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val anim = translateAnim.value // read happens at draw time only
                return LinearGradientShader(
                    from = Offset(anim - 200f, 0f),
                    to = Offset(anim, 0f),
                    colors = colors,
                    tileMode = TileMode.Clamp
                )
            }
        }
    }
}