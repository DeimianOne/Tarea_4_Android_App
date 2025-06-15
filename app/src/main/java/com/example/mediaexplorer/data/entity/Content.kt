package com.example.mediaexplorer.data.entity

import com.google.gson.annotations.SerializedName

data class Content(
    val id: Int = 0,

    val name: String,

    @SerializedName("description")
    val information: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("image")
    val contentImageUri: String?,

    val duration: Int? = null,

    @SerializedName("number_of_episodes")
    val cantCap: Int? = null,

    @SerializedName("genre")
    val typeGender: String? = null,

)


