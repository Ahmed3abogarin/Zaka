package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.scan.ScanHistoryItem
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.TextPrimary

@Composable
fun ScanHistorySection(
    items: List<ScanHistoryItem>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .background(Color.White),
    ) {
        // Section header
        Text(
            text       = "عمليات مسح سابقة",
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        )

        HorizontalDivider(color = BorderColor, thickness = 0.5.dp)

        items.forEachIndexed { index, item ->
            ScanHistoryRow(item = item)
            if (index < items.lastIndex) {
                HorizontalDivider(
                    modifier  = Modifier.padding(horizontal = 16.dp),
                    color     = BorderColor,
                    thickness = 0.5.dp,
                )
            }
        }
    }
}