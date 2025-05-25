package com.example.mediaexplorer.ui.views.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class CategoryEntryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    // Estado interno del formulario
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    val categories: StateFlow<List<Category>> = repository.getAllCategoriesStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Métodos para actualizar valores desde la UI
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onImageUriChanged(newUri: String?) {
        _imageUri.value = newUri
    }

    // Guardar categoría
    fun saveCategory() {
        viewModelScope.launch {
            val nameTrimmed = name.value.trim()

            // Obtener las categorías existentes
            val existing = repository.getAllCategoriesStream().first()

            // Verificar duplicados
            if (existing.any { it.name.equals(nameTrimmed, ignoreCase = true) }) {
                _errorMessage.value = "Ya existe una categoría con ese nombre"
                return@launch
            }

            // Si no hay duplicado, guardar
            val category = Category(
                name = nameTrimmed,
                categoryImageUri = imageUri.value
            )
            repository.insertCategory(category)

            // Limpiar campos y mensaje
            _name.value = ""
            _imageUri.value = null
            _errorMessage.value = ""
        }
    }

}