package com.example.mediaexplorer.ui.components_utils

import com.example.mediaexplorer.data.entity.Category

fun validateCategoryName(
    name: String,
    existingCategories: List<Category>,
    currentId: Int? = null
): String? {
    val trimmedName = name.trim()

    if (trimmedName.length < 3) {
        return "El nombre debe tener al menos 3 caracteres"
    }

    if (trimmedName.length > 12) {
        return "El nombre no puede tener más de 12 caracteres"
    }

    val isDuplicate = existingCategories.any {
        it.name.equals(trimmedName, ignoreCase = true) &&
                (currentId == null || it.id != currentId)
    }

    if (isDuplicate) {
        return "Ya existe una categoría con ese nombre"
    }

    return null
}