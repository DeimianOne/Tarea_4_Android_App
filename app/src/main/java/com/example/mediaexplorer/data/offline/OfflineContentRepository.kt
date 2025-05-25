package com.example.mediaexplorer.data.offline

import com.example.mediaexplorer.data.dao.ContentDao
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.Flow

class OfflineContentRepository(private val contentDao: ContentDao) : ContentRepository {

    override fun getContentsByCategoryStream(categoryName: String): Flow<List<Content>> =
        contentDao.getByCategory(categoryName)

    override fun getContentStream(id: Int): Flow<Content?> = contentDao.getById(id)

    override suspend fun insertContent(content: Content) = contentDao.insert(content)

    override suspend fun deleteContent(content: Content) = contentDao.delete(content)

    override suspend fun updateContent(content: Content) = contentDao.update(content)
}