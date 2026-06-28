package com.vtol.zaka.presentation.result.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun ResultHeader(topic: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "نتيجة الإختبار",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = topic,
            fontSize = 14.sp,
            color = TextSecond,
        )
    }
}