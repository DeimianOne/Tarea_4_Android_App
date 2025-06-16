package com.example.mediaexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediaexplorer.ui.theme.MediaExplorerTheme
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import com.example.mediaexplorer.ui.views.InitialLoadViewModel
import com.example.mediaexplorer.ui.views.initial.LoadingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaExplorerTheme {
                // Ejecutar solo una vez al iniciar la app
                val viewModel: InitialLoadViewModel = viewModel(factory = AppViewModelProvider.Factory)
                val initialDone by viewModel.initialLoadDone.collectAsState()
                LaunchedEffect(Unit) {
                    viewModel.loadInitialData(context = this@MainActivity)
                }
                if (initialDone) {
                    Navigation()
                } else {
                    LoadingScreen() // una Composable que muestre un spinner o logo
                }
            }
        }
    }
}
