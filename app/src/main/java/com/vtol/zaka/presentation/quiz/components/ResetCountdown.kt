package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.GrayBg
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.TextSecond
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun ResetCountdown() {
    var timeLeft by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val now = Calendar.getInstance()
            val midnight = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.DAY_OF_MONTH, 1)
            }
            val diff = midnight.timeInMillis - now.timeInMillis
            val hours = diff / 3_600_000
            val minutes = (diff % 3_600_000) / 60_000
            val seconds = (diff % 60_000) / 1_000
            timeLeft = "%02d:%02d:%02d".format(hours, minutes, seconds)
            delay(1000L)
        }
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(GrayBg)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = timeLeft,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Purple500,
        )
        Text("يعاد الضبط خلال", fontSize = 13.sp, color = TextSecond)
    }
}