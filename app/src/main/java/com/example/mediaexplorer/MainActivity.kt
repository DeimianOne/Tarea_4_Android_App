package com.example.mediaexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediaexplorer.ui.theme.MediaExplorerTheme
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import com.example.mediaexplorer.ui.views.InitialLoadViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaExplorerTheme {
                val viewModel: InitialLoadViewModel = viewModel(factory = AppViewModelProvider.Factory)

                // Ejecutar solo una vez al iniciar la app
                LaunchedEffect(Unit) {
                    viewModel.loadInitialData(context = this@MainActivity)
                }
                Navigation()
            }
        }
    }
}
