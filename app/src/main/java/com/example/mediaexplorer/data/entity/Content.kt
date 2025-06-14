package com.example.mediaexplorer.data.entity

import com.google.gson.annotations.SerializedName

data class Content(
    val id: Int = 0,

    val name: String,

    val information: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("content_image_uri")
    val contentImageUri: String?,

    val duration: Int? = null,

    @SerializedName("cant_cap")
    val cantCap: Int? = null,

    @SerializedName("type_gender")
    val typeGender: String? = null,

    @SerializedName("type_content")
    val typeContent: String? = null
)


