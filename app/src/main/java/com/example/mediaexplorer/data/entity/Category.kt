package com.example.mediaexplorer.data.entity

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int = 0,

    val name: String,

    @SerializedName("category_image_uri")
    val categoryImageUri: String?
)


