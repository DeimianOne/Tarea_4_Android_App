package com.example.mediaexplorer.ui.views.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryEntryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    // Estado interno del formulario
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    // Métodos para actualizar valores desde la UI
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onImageUriChanged(newUri: String?) {
        _imageUri.value = newUri
    }

    // Guardar categoría
    fun saveCategory() {
        val category = Category(
            name = _name.value,
            categoryImageUri = _imageUri.value
        )
        viewModelScope.launch {
            repository.insertCategory(category)

            // Opcional: limpia después de guardar
            _name.value = ""
            _imageUri.value = null
        }
    }
}