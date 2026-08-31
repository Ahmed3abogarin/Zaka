package com.vtol.zaka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.vtol.zaka.presentation.graph.AppHost
import com.vtol.zaka.presentation.update.FlexibleUpdateDialog
import com.vtol.zaka.presentation.update.ForceUpdateScreen
import com.vtol.zaka.presentation.update.UpdateState
import com.vtol.zaka.presentation.update.UpdateViewModel
import com.vtol.zaka.ui.theme.ZakaTheme
import com.vtol.zaka.util.openUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZakaTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    val updateViewModel: UpdateViewModel = hiltViewModel()
                    val updateState by updateViewModel.updateState.collectAsState()
                    val updateUrl by updateViewModel.updateUrl.collectAsState()
                    val context = LocalContext.current

                    Surface(modifier = Modifier.fillMaxSize()) {
                        when (updateState) {
                            UpdateState.Force -> {
                                ForceUpdateScreen(onUpdateClick = { context.openUrl(updateUrl) })
                            }
                            else -> {
                                AppHost()
                                if (updateState == UpdateState.Flexible) {
                                    FlexibleUpdateDialog(
                                        onUpdateClick = { context.openUrl(updateUrl) },
                                        onDismiss = { updateViewModel.dismissFlexibleUpdate() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
