package com.vtol.zaka.presentation.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vtol.zaka.BuildConfig
import com.vtol.zaka.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class UpdateState {
    object None : UpdateState()
    object Flexible : UpdateState()
    object Force : UpdateState()
}

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.None)
    val updateState = _updateState.asStateFlow()

    private val _updateUrl = MutableStateFlow("https://play.google.com/store/apps/details?id=com.vtol.zaka")
    val updateUrl = _updateUrl.asStateFlow()

    // To ensure it only checks once per session
    private var hasCheckedForUpdate = false

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivityObserver.observe()
            .onEach { status ->
                if (status == ConnectivityObserver.Status.Available && !hasCheckedForUpdate) {
                    checkForUpdate()
                }
            }
            .launchIn(viewModelScope)
    }

    fun checkForUpdate() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val forceVersion = remoteConfig.getLong("force_update_version").toInt()
                    val latestVersion = remoteConfig.getLong("latest_version_code").toInt()
                    val url = remoteConfig.getString("update_url")
                    
                    if (url.isNotEmpty()) {
                        _updateUrl.value = url
                    }

                    val currentVersion = BuildConfig.VERSION_CODE
                    
                    Log.d("UpdateCheck", "Current: $currentVersion, Force: $forceVersion, Latest: $latestVersion")

                    when {
                        currentVersion < forceVersion -> {
                            _updateState.update { UpdateState.Force }
                        }
                        currentVersion < latestVersion -> {
                            _updateState.update { UpdateState.Flexible }
                        }
                        else -> {
                            _updateState.update { UpdateState.None }
                        }
                    }
                    hasCheckedForUpdate = true
                }
            }
    }

    fun dismissFlexibleUpdate() {
        _updateState.update { UpdateState.None }
    }
}
