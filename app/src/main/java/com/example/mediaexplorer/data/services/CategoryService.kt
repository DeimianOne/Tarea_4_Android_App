package com.example.mediaexplorer.data.services

import com.example.mediaexplorer.data.entity.Category
import retrofit2.Response
import retrofit2.http.*

interface CategoryService {
    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @GET("api/categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Category

    @POST("api/categories")
    suspend fun createCategory(@Body category: Category): Response<Unit>

    @PUT("api/categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: Category): Response<Unit>

    @DELETE("api/categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Unit>
}
