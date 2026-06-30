package com.vtol.zaka.presentation.scan

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.domain.models.ScanHistoryItem
import com.vtol.zaka.presentation.scan.components.AiTipCard
import com.vtol.zaka.presentation.scan.components.CameraButton
import com.vtol.zaka.presentation.scan.components.ScanHistorySection
import com.vtol.zaka.presentation.scan.components.SecondaryActionCard
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal100
import com.vtol.zaka.ui.theme.Teal400
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun ScanContent(modifier: Modifier = Modifier, generateFromPdf: (ByteArray) -> Unit) {
    val context = LocalContext.current

    // PDF picker
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bytes = context.contentResolver
                .openInputStream(it)
                ?.readBytes()

            if (bytes != null) {
                generateFromPdf(bytes)
            }
        }
    }

    // Camera picker
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {

        }
    }

    // gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {

        }
    }


    val history = listOf(
        ScanHistoryItem(
            title = "ملخص الكيمياء.pdf",
            subtitle = "منذ ساعتين",
            icon = Icons.Outlined.PictureAsPdf,
            iconBg = Teal100,
            iconTint = Teal400,
        ),
        ScanHistoryItem(
            title = "صفحة ٤٢ - فيزياء",
            subtitle = "يوم أمس",
            icon = Icons.Outlined.Image,
            iconBg = Purple100,
            iconTint = Purple500,
        )
    )

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
        item { CameraButton { cameraLauncher.launch(null) } }

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
                        galleryLauncher.launch("image/*")
                    }
                )
                SecondaryActionCard(
                    label = "ملف PDF",
                    icon = Icons.Outlined.PictureAsPdf,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        pdfLauncher.launch("application/pdf")
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
            ScanHistorySection(
                items = history,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        }
    }
}