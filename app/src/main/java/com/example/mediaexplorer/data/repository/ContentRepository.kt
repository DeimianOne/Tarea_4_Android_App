package com.example.mediaexplorer.data.repository

import com.example.mediaexplorer.data.entity.Content
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    fun getContentsByCategoryStream(categoryName: String): Flow<List<Content>>

    fun getContentStream(id: Int): Flow<Content?>

    suspend fun insertContent(content: Content)

    suspend fun deleteContent(content: Content)

    suspend fun updateContent(content: Content)

    suspend fun getContentById(id: Int): Content?
}
