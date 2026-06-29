package com.vtol.zaka.presentation.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.progress.WeeklyActivity

@Composable
fun WeeklyActivityCard(activity: List<WeeklyActivity>) {
    val maxCount = activity.maxOf { it.count }.coerceAtLeast(1)
    val today    = 3   // 0-based index of today (أربع = index 3 in the sample)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text       = "نشاطك الأسبوعي",
            fontSize   = 18.sp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF111827),
            modifier   = Modifier.fillMaxWidth(),
            textAlign  = TextAlign.End,
        )

        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .height(140.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.Bottom,
        ) {
            activity.forEachIndexed { index, item ->
                ActivityBar(
                    day       = item.day,
                    fraction  = item.count.toFloat() / maxCount,
                    isToday   = index == today,
                )
            }
        }
    }
}