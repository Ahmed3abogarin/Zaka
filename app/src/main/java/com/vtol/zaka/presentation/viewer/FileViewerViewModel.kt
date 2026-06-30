package com.vtol.zaka.presentation.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vtol.zaka.data.local.FileStorageManager
import com.vtol.zaka.domain.models.ScanType
import com.vtol.zaka.domain.usecases.GetSessionDetail
import com.vtol.zaka.presentation.graph.FileViewerRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSessionDetail: GetSessionDetail,
    private val fileStorageManager: FileStorageManager,
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<FileViewerRoute>().sessionId

    private val _state = MutableStateFlow<FileViewerUiState>(FileViewerUiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val session = getSessionDetail(sessionId)
            if (session == null) {
                _state.update { FileViewerUiState.Error("لم يتم العثور على الملف") }
                return@launch
            }
            val file = fileStorageManager.getFile(session.sourceFilePath)
            if (!file.exists()) {
                _state.update { FileViewerUiState.Error("الملف غير موجود") }
                return@launch
            }
            _state.update { FileViewerUiState.Success(file, session.scanType) }
        }
    }
}

sealed interface FileViewerUiState {
    data object Loading : FileViewerUiState
    data class Success(val file: File, val scanType: ScanType) : FileViewerUiState
    data class Error(val message: String) : FileViewerUiState
}