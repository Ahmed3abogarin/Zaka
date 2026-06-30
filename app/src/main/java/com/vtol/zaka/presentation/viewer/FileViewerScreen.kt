package com.vtol.zaka.presentation.viewer

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vtol.zaka.domain.models.ScanType

@Composable
fun FileViewerScreen(
    viewModel: FileViewerViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    when (val s = state) {
        is FileViewerUiState.Loading -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        }
        is FileViewerUiState.Error -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) { Text(s.message) }
        }
        is FileViewerUiState.Success -> {
            when (s.scanType) {
                ScanType.IMAGE -> {
                    // Render image directly in Compose
                    AsyncImage(
                        model = s.file,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                    )
                }
                ScanType.PDF -> {
                    // Option A: open with system PDF viewer
                    LaunchedEffect(Unit) {
                        val uri = FileProvider.getUriForFile(
                            context, "${context.packageName}.fileprovider", s.file
                        )
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(uri, "application/pdf")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(intent)
                        onBack()
                    }
                }
            }
        }
    }
}