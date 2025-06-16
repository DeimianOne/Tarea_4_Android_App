package com.example.mediaexplorer.data.services

import com.example.mediaexplorer.data.entity.Content
import retrofit2.Response
import retrofit2.http.*

interface ContentService {
    @GET("api/categories/{name}/contents")
    suspend fun getContentsByCategory(@Path("name") categoryName: String): List<Content>

    @GET("api/contents/{id}")
    suspend fun getContentById(@Path("id") id: Int): Content

    @POST("api/contents")
    suspend fun createContent(@Body content: Content): Response<Unit>

    @PUT("api/contents/{id}")
    suspend fun updateContent(@Path("id") id: Int, @Body content: Content): Response<Unit>

    @DELETE("api/contents/{id}")
    suspend fun deleteContent(@Path("id") id: Int): Response<Unit>
}
