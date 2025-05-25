package com.example.mediaexplorer.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Nombre único de la categoría (e.g., "Películas", "Series")
    @ColumnInfo(name = "name") val name: String,

    // Imagen asociada a la categoría (ej: ícono general de "películas")
    @ColumnInfo(name = "category_image_uri") val categoryImageUri: String?
)

