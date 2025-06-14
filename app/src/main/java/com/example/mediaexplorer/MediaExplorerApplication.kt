package com.example.mediaexplorer

import android.app.Application
import com.example.mediaexplorer.data.AppContainer
import com.example.mediaexplorer.data.MediaExplorerAppContainer

class MediaExplorerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = MediaExplorerAppContainer() // <- ✅ ESTE es tu contenedor actual
    }
}

