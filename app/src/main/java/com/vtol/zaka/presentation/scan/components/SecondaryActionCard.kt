package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.GrayBg
import com.vtol.zaka.ui.theme.Purple500
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.vtol.zaka.ui.theme.TextPrimary

@Composable
fun SecondaryActionCard(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val alpha = if (enabled) 1f else 0.5f
    val iconTint = if (enabled) Purple500 else Color.Gray
    val textColor = if (enabled) TextPrimary else Color.Gray

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(0.5.dp, if (enabled) BorderColor else BorderColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .background(GrayBg)
            .clickable { onClick() }
            .padding(vertical = 20.dp)
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(28.dp),
        )
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
        )
    }
}
