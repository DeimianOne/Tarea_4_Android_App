package com.example.mediaexplorer.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "content",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["name"],
        childColumns = ["category_name"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["category_name"])]
)
data class Content(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Título del contenido (película, serie, anime...)
    val name: String,

    // Información adicional sobre el contenido
    val information: String,

    // Nombre de la categoría a la que pertenece (clave foránea)
    @ColumnInfo(name = "category_name") val categoryName: String,

    // Imagen específica del contenido (ej: carátula de la película)
    @ColumnInfo(name = "content_image_uri") val contentImageUri: String?,

    // Campos opcionales según el tipo
    val duration: Int? = null,         // Solo para películas
    val cantCap: Int? = null,          // Series o anime
    val typeGender: String? = null,    // Anime
    val typeContent: String? = null    // Otros contenidos
)

