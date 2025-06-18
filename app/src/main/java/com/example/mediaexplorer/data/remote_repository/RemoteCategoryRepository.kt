package com.example.mediaexplorer.data.remote_repository

import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.services.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCategoryRepository(private val api: CategoryService) : CategoryRepository {

    override fun getAllCategoriesStream(): Flow<List<Category>> = flow {
        emit(api.getCategories())
    }

    override fun getCategoryStream(id: Int): Flow<Category?> = flow {
        emit(api.getCategoryById(id))
    }

    override suspend fun insertCategory(category: Category) {
        api.createCategory(category)
    }

    override suspend fun insertCategoryWithImage(name: String, imageUri: String?, context: Context) {
        val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = imageUri?.let {
            val file = File(uriToFilePath(context, it))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }

        api.createCategory(namePart, imagePart)
    }

    override suspend fun deleteCategory(category: Category) {
        api.deleteCategory(category.id)
    }

    override suspend fun updateCategory(category: Category) {
        api.updateCategory(category.id, category)
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return api.getCategoryById(id)
    }
}