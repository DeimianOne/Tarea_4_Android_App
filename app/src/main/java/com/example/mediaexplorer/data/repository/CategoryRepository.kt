package com.example.mediaexplorer.data.repository

import com.example.mediaexplorer.data.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategoriesStream(): Flow<List<Category>>

    fun getCategoryStream(id: Int): Flow<Category?>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun getCategoryById(id: Int): Category?
}
