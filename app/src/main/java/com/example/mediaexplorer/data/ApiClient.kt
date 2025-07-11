package com.example.mediaexplorer.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/"
//    private const val BASE_URL = "http://192.168.157.207:8000/"

    fun create(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }
}
