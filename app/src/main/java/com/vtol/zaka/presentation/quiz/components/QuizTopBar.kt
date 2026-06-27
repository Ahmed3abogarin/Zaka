package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.TextSecond
import com.vtol.zaka.ui.theme.TimerBg
import com.vtol.zaka.ui.theme.TimerText

@Composable
fun QuizTopBar(
    timerText: String,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Close button on the left (LTR left = RTL right, but we're in RTL so this is the end)
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "إغلاق",
                tint = TextSecond,
                modifier = Modifier.size(20.dp),
            )
        }

        // Timer pill on the right (RTL start = visual left)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(TimerBg)
                .padding(horizontal = 14.dp, vertical = 7.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Text(
                    text = timerText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TimerText,
                )
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = TimerText,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}