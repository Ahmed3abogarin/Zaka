package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700

@Composable
fun CameraButton(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val alpha = if (enabled) 1f else 0.5f
    val gradientColors = if (enabled) {
        listOf(Purple500, Purple700)
    } else {
        listOf(Color.Gray, Color.DarkGray)
    }

    Box(contentAlignment = Alignment.Center) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = if (enabled) {
                            listOf(Purple500.copy(alpha = 0.25f), Color.Transparent)
                        } else {
                            listOf(Color.Gray.copy(alpha = 0.1f), Color.Transparent)
                        }
                    )
                )
        )
        // Inner circle button
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(width = 8.dp, color = if (enabled) Color(0x33E8DDFF) else Color(0x1A000000), shape = CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = gradientColors
                    )
                )
                .clickable(enabled = true) { onClick() }, // We handle the toast in the screen
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(alpha)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "افتح الكاميرا",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            }
        }
    }
}