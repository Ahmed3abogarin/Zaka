package com.vtol.zaka.presentation.scan

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.vtol.zaka.data.local.getFileName
import com.vtol.zaka.presentation.quiz.QuizViewModel
import com.vtol.zaka.presentation.quiz.components.QuotaExceededSheet

import android.widget.Toast

@Composable
fun ScanScreen(
    viewModel: QuizViewModel,
    navigateToViewer: (Int) -> Unit,
    navigateToQuiz: () -> Unit
) {
    val context = LocalContext.current

    val activity = LocalActivity.current
    val recentSessions by viewModel.recentScans.collectAsState()
    val state by viewModel.state.collectAsState()
    
    val quotaStatus by viewModel.quotaStatus.collectAsState()
    val isAdAvailable by viewModel.isAdAvailable.collectAsState()
    var showQuotaSheet by remember { mutableStateOf(false) }

    val rewardedAdManager = viewModel.rewardedAdManager

    // PDF picker
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes() ?: return@let
            val fileName = getFileName(context, uri)
            viewModel.generateFromPdf(bytes, fileName)
            navigateToQuiz()
        }
    }

    // Camera picker
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            viewModel.generateFromImage(it)
            navigateToQuiz()
        }
    }

    // gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.generateFromImageUri(it, context)
            navigateToQuiz()
        }
    }


    if (showQuotaSheet) {
        QuotaExceededSheet(
            remaining = quotaStatus.remaining,
            isAdAvailable = isAdAvailable,
            onDismiss = { showQuotaSheet = false },
            onUpgrade = { /* navigate to paywall */ },
            onWatchAd = {
                activity?.let {
                    rewardedAdManager.showAd(
                        activity = activity,
                        onRewarded = {
                            viewModel.onUserRewarded()
                            showQuotaSheet = false
                            // now trigger the scan they originally wanted
                            pdfLauncher.launch("application/pdf")
                        },
                        onDismissed = {
                            // user closed ad without finishing — don't grant reward
                        },
                    )
                }
            },
        )
    }

    fun handleScanAction(action: () -> Unit) {
        if (state.isOffline) {
            Toast.makeText(context, "عذراً، لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة والمحاولة مرة أخرى.", Toast.LENGTH_SHORT).show()
        } else {
            if (quotaStatus.canPlay) action()
            else showQuotaSheet = true
        }
    }

    ScanContent(
        quotaStatus = quotaStatus,
        recentSessions = recentSessions,
        isOffline = state.isOffline,
        navigateToViewer = navigateToViewer,
        launchPdf = {
            handleScanAction { pdfLauncher.launch("application/pdf") }
        },
        launchCamera = {
            handleScanAction { cameraLauncher.launch(null) }
        },
        launchGallery = {
            handleScanAction { galleryLauncher.launch("image/*") }
        },
        showBottonSheet = {
            showQuotaSheet = true
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScanScreenPreview() {
    MaterialTheme {

    }
}
