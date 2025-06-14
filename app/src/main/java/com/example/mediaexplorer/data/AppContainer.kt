package com.example.mediaexplorer.data

import com.example.mediaexplorer.data.services.CategoryService
import com.example.mediaexplorer.data.services.ContentService
import com.example.mediaexplorer.data.remote_repository.RemoteCategoryRepository
import com.example.mediaexplorer.data.remote_repository.RemoteContentRepository
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository

interface AppContainer {
    val categoryRepository: CategoryRepository
    val contentRepository: ContentRepository
}

class MediaExplorerAppContainer : AppContainer {

    private val retrofit = ApiClient.create()

    override val categoryRepository: CategoryRepository by lazy {
        val service = retrofit.create(CategoryService::class.java)
        RemoteCategoryRepository(service)
    }

    override val contentRepository: ContentRepository by lazy {
        val service = retrofit.create(ContentService::class.java)
        RemoteContentRepository(service)
    }

}
