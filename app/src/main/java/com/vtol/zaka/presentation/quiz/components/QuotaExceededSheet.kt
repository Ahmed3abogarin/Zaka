package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotaExceededSheet(
    remaining: Int,
    onDismiss: () -> Unit,
    onUpgrade: () -> Unit,
    onWatchAd: () -> Unit,
    isAdAvailable: Boolean,   // ← not always available
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("🔒", fontSize = 48.sp)

            Text(
                text = "وصلت لحد اليوم",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )

            Text(
                text = "المستخدم المجاني يحصل على ٣ اختبارات يومياً.",
                fontSize = 14.sp,
                color = TextSecond,
                textAlign = TextAlign.Center,
            )

            ResetCountdown()

            Spacer(Modifier.height(4.dp))

            // ── Option 1: Watch ad for one extra quiz ─────────────────────
            if (isAdAvailable) {
                OutlinedButton(
                    onClick = onWatchAd,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, Purple500),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "شاهد إعلاناً للحصول على اختبار إضافي",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Purple500,
                        )
                        Text("🎬", fontSize = 18.sp)
                    }
                }
            }

            // ── Option 2: Upgrade ─────────────────────────────────────────
            Button(
                onClick = onUpgrade,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple700),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "ترقَّ للنسخة المميزة — اختبارات غير محدودة",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Text("✨", fontSize = 18.sp)
                }
            }

            // ── Option 3: Dismiss ─────────────────────────────────────────
            TextButton(onClick = onDismiss) {
                Text("ليس الآن", color = TextSecond)
            }
        }
    }
}