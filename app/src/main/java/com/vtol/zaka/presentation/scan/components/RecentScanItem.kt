package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.models.ScanType
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal100
import com.vtol.zaka.ui.theme.Teal400
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun RecentScanItem(scan: QuizSession, onClick: () -> Unit) {
    val (icon, iconBg, iconTint) = when (scan.scanType) {
        ScanType.PDF -> Triple(Icons.Outlined.PictureAsPdf, Teal100, Teal400)
        ScanType.IMAGE -> Triple(Icons.Outlined.Image, Purple100, Purple500)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }   // navigate to QuizDetailsRoute(scan.id)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = iconTint, modifier = Modifier.size(20.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                scan.sourceFileName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(Modifier.height(3.dp))
            Text(scan.relativeTime, fontSize = 12.sp, color = TextSecond)
        }


        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = TextSecond,
            modifier = Modifier.size(20.dp),
        )
    }
}