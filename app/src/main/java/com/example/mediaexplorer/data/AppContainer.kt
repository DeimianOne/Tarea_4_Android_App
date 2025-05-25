package com.example.mediaexplorer.data

import android.content.Context
import com.example.mediaexplorer.data.offline.OfflineCategoryRepository
import com.example.mediaexplorer.data.offline.OfflineContentRepository
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository

class AppContainer(private val context: Context) {

    // Repositorios: se crean solo si se usan
    val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(AppDatabase.getDatabase(context).categoryDao())
    }

    val contentRepository: ContentRepository by lazy {
        OfflineContentRepository(AppDatabase.getDatabase(context).contentDao())
    }
}
