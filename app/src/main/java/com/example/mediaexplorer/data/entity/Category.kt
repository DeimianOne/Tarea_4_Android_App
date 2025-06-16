package com.example.mediaexplorer.data.entity

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,

    val name: String,

    @SerializedName("image")
    val categoryImageUri: String?
)


data class CategoryUpdateDTO(
    val name: String,
    @SerializedName("image")
    val categoryImageUri: String?
)
