package com.vtol.zaka.presentation.graph.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple200
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700

@Composable
fun ScanFab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .offset(y = (-22).dp)            // lifts the circle above the bar
    ) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Purple200, Color.Transparent),
                        radius = 120f
                    )
                )
                .shadow(
                    elevation = if (selected) 2.dp else 0.dp,
                    shape = CircleShape,
                    ambientColor = Purple500,
                    spotColor = Purple100
                )
        )
        // Purple circle button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Purple500, Purple700)
                    )
                )
                .clickable(onClick = onClick)
        ) {
            // Scan / document icon  — replace with your actual icon resource
            Icon(
                imageVector = Icons.Outlined.DocumentScanner,   // swap for real scan icon
                contentDescription = "مسح",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}