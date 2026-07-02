package com.vtol.zaka.presentation.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.domain.usecases.QuotaStatus
import com.vtol.zaka.presentation.scan.components.AiTipCard
import com.vtol.zaka.presentation.scan.components.CameraButton
import com.vtol.zaka.presentation.scan.components.RecentScansSection
import com.vtol.zaka.presentation.scan.components.SecondaryActionCard
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun ScanContent(
    quotaStatus: QuotaStatus,
    recentSessions: List<QuizSession>,
    navigateToViewer: (Int) -> Unit,
    showBottonSheet: () -> Unit,
    launchPdf: () -> Unit,
    launchCamera: () -> Unit,
    launchGallery: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // ── Top spacing ───────────────────────────────────────────────────
        item { Spacer(Modifier.height(56.dp)) }

        // ── Header text ───────────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "امسح ملاحظاتك",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "حوّل أوراقك الدراسية إلى ملخصات ذكية في ثوان",
                    fontSize = 14.sp,
                    color = TextSecond,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )
            }
        }

        item { Spacer(Modifier.height(40.dp)) }

        // ── Camera button ─────────────────────────────────────────────────
        item {
            CameraButton {
                if (quotaStatus.canPlay) launchCamera()
                else showBottonSheet()
            }
        }

        item { Spacer(Modifier.height(40.dp)) }

        // ── Secondary action cards ─────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SecondaryActionCard(
                    label = "من المعرض",
                    icon = Icons.Outlined.Image,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (quotaStatus.canPlay) launchGallery()
                        else showBottonSheet()
                    }
                )
                SecondaryActionCard(
                    label = "ملف PDF",
                    icon = Icons.Outlined.PictureAsPdf,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (quotaStatus.canPlay) launchPdf()
                        else showBottonSheet()
                    }
                )
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        // ── AI tip card ───────────────────────────────────────────────────
        item {
            AiTipCard(modifier = Modifier.padding(horizontal = 20.dp))
        }

        item { Spacer(Modifier.height(24.dp)) }

        // ── Scan history ──────────────────────────────────────────────────
        item {
            RecentScansSection(
                modifier = Modifier.padding(horizontal = 20.dp),
                scans = recentSessions,
                onClick = navigateToViewer
            )
        }
    }
}