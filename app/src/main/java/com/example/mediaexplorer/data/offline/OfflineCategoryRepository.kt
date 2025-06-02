package com.example.mediaexplorer.data.offline

import com.example.mediaexplorer.data.dao.CategoryDao
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {

    override fun getAllCategoriesStream(): Flow<List<Category>> = categoryDao.getAll()

    override fun getCategoryStream(id: Int): Flow<Category?> = categoryDao.getById(id)

    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

    override suspend fun updateCategory(category: Category) = categoryDao.update(category)

    override suspend fun getCategoryById(id: Int): Category? = categoryDao.getCategoryById(id)
}