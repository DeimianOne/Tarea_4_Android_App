package com.example.mediaexplorer.data.remote_repository

import android.content.Context
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.services.CategoryService
import com.example.mediaexplorer.ui.components_utils.uriToFilePath
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RemoteCategoryRepository(private val api: CategoryService) : CategoryRepository {

    override fun getAllCategoriesStream(): Flow<List<Category>> = flow {
        emit(api.getCategories())
    }

    override fun getCategoryStream(id: Int): Flow<Category?> = flow {
        emit(api.getCategoryById(id))
    }

    override suspend fun insertCategory(category: Category) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

//    override suspend fun updateCategory(category: Category) {
//        api.updateCategory(category.id, category)
//    }

    override suspend fun updateCategoryWithImage(
        id: Int,
        name: String,
        imageUri: String?,
        context: Context
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return api.getCategoryById(id)
    }
}