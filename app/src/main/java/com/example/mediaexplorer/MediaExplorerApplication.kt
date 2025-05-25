package com.example.mediaexplorer

import android.app.Application
import com.example.mediaexplorer.data.AppContainer

class MediaExplorerApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
