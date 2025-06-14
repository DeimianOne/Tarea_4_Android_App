package com.example.mediaexplorer.data.remote_repository

import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.ContentRepository
import com.example.mediaexplorer.data.services.ContentService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteContentRepository(private val api: ContentService) : ContentRepository {

    override fun getContentsByCategoryStream(categoryName: String): Flow<List<Content>> = flow {
        emit(api.getContentsByCategory(categoryName))
    }

    override fun getContentStream(id: Int): Flow<Content?> = flow {
        emit(api.getContentById(id))
    }

    override suspend fun insertContent(content: Content) {
        api.createContent(content)
    }

    override suspend fun deleteContent(content: Content) {
        api.deleteContent(content.id)
    }

    override suspend fun updateContent(content: Content) {
        api.updateContent(content.id, content)
    }

    override suspend fun getContentById(id: Int): Content? {
        return api.getContentById(id)
    }
}