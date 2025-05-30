package com.example.mediaexplorer.ui.views.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class CategoryEditViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var categoryId: Int? = null

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Cargar contenido para edición
    fun loadCategory(category: Category) {
        categoryId = category.id
        _name.value = category.name
        _imageUri.value = category.categoryImageUri
    }

    fun loadCategoryById(categoryId: Int) {
        viewModelScope.launch {
            val category = categoryRepository.getCategoryById(categoryId)
            category?.let { loadCategory(it) }
        }
    }

    fun onNameChanged(value: String) { _name.value = value }
    fun onImageUriChanged(value: String?) { _imageUri.value = value }

    fun updateCategory() {
        viewModelScope.launch {
            val nameTrimmed = name.value.trim()

            if (nameTrimmed.isBlank()) {
                _errorMessage.value = "El nombre no puede estar vacío"
                return@launch
            }

            // Verificar duplicados
            val existing = categoryRepository.getAllCategoriesStream().first()
            if (existing.any { it.name.equals(nameTrimmed, ignoreCase = true) && it.id != categoryId }) {
                _errorMessage.value = "Ya existe una categoría con ese nombre"
                return@launch
            }

            val updatedCategory = Category(
                id = categoryId ?: return@launch,
                name = nameTrimmed,
                categoryImageUri = imageUri.value,
            )
            categoryRepository.updateCategory(updatedCategory)
            // Log para depuración (se muestra en Logcat)
            Log.d("CategoryEditViewModel", "Categoría actualizada: ID=${updatedCategory.id}, Nombre=${updatedCategory.name}")
            _errorMessage.value = ""
        }
    }
}