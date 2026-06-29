package com.vtol.zaka.presentation.graph.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.LabelActive
import com.vtol.zaka.ui.theme.LabelIdle
import com.vtol.zaka.ui.theme.NavBg

/**
 * Arabic bottom navigation bar with three tabs:
 *   الرئيسية (Home)  |  مسح (Scan — elevated FAB)  |  تقدمي (Progress)
 *
 * RTL note: wrap this inside `CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)`
 * at your theme/screen level so Arabic ordering is applied automatically.
 */
@Composable
fun ArabicBottomNavBar(
    selectedIndex: Int = 0,           // 0 = Home, 1 = Scan, 2 = Progress
    onItemSelected: (Int) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // Bar background — raised card with rounded top corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(NavBg)
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // Right side in Arabic RTL → الرئيسية (Home)
                NavIconItem(
                    icon = Icons.Outlined.Home,
                    label = "الرئيسية",
                    selected = selectedIndex == 0,
                    onClick = { onItemSelected(0) },
                    modifier = Modifier.weight(1f)
                )

                // Center placeholder — ScanFab floats above
                Spacer(Modifier.weight(1f))

                // Left side in Arabic RTL → تقدمي (Progress)
                NavIconItem(
                    icon = Icons.Outlined.BarChart,
                    label = "تقدمي",
                    selected = selectedIndex == 2,
                    onClick = { onItemSelected(2) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Floating scan button — centered horizontally, overlaps the bar top
        ScanFab(
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // Label under scan button
        Text(
            text = "مسح",
            fontSize = 12.sp,
            fontWeight = if (selectedIndex == 1) FontWeight.Bold else FontWeight.Normal,
            color = if (selectedIndex == 1) LabelActive else LabelIdle,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF2F0FA)
@Composable
fun ArabicBottomNavBarPreview() {
    var selected by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(NavBg),
        contentAlignment = Alignment.BottomCenter
    ) {
        ArabicBottomNavBar(
            selectedIndex = selected,
            onItemSelected = { selected = it }
        )
    }
}