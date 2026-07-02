package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.Orange100
import com.vtol.zaka.ui.theme.Orange400
import com.vtol.zaka.ui.theme.RedError
import com.vtol.zaka.ui.theme.RedLight

@Composable
fun QuotaIndicator(remaining: Int) {
    if (remaining >= 3) return   // don't show when full

    val color = if (remaining == 0) RedError else Orange400
    val bg = if (remaining == 0) RedLight else Orange100

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 14.dp, vertical = 6.dp),
    ) {
        Text(
            text = if (remaining == 0) "انتهت اختباراتك اليوم 🔒"
            else "تبقى لك $remaining اختبار اليوم",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = color,
        )
    }
}