package com.vtol.zaka

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.vtol.zaka.ui.theme.ZakaTheme


@Composable
fun HomeScreen2(
    onTopicSelected: (String) -> Unit,
    onScanClick: () -> Unit,
    onUploadClick: () -> Unit,
    onProgressClick: () -> Unit
) {
    val firstName = "طالب"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onProgressClick) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF2A2A4A), RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = firstName.first().toString(),
                            color = Color(0xFF7F77DD),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "مرحباً، $firstName",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        text = "جاهز للاختبار؟",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }

        // Hero card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF534AB7))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "ابدأ اختباراً جديداً",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "أضف نصاً أو صورة أو PDF وسنولّد أسئلة ذكية بالذكاء الاصطناعي",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Scan / Upload buttons
        item {
            Text(
                text = "إنشاء اختبار",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InputOptionCard(
                    icon = Icons.Default.CameraAlt,
                    label = "صوّر صفحة",
                    modifier = Modifier.weight(1f),
                    onClick = onScanClick
                )
                InputOptionCard(
                    icon = Icons.Default.Upload,
                    label = "رفع PDF",
                    modifier = Modifier.weight(1f),
                    onClick = onUploadClick
                )
            }
        }

        // Topics
        item {
            Text(
                text = "اختر موضوعاً",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(3) {
                    TopicChip(onClick = { onTopicSelected("topic.displayNameAr") })
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun InputOptionCard(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2A2A4A))
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF7F77DD), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = label, fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun TopicChip(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2A2A4A))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Place holder",
            fontSize = 13.sp,
            color = Color(0xFFAFA9EC),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun HomeScreenPre2(){
    ZakaTheme {
        HomeScreen2(
            onScanClick = {},
            onUploadClick = {},
            onProgressClick = {},
            onTopicSelected = {}
        )
    }
}