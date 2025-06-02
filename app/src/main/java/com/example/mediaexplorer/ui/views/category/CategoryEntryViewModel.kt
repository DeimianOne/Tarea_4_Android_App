package com.example.mediaexplorer.ui.views.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.ui.components_utils.validateCategoryName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first

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

    // variable para comprobar si se guardo correctamente
    private val _savedSuccessfully = MutableStateFlow(false)
    val savedSuccessfully: StateFlow<Boolean> = _savedSuccessfully

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
            val error = validateCategoryName(nameTrimmed, existing)

            if (error != null) {
                _errorMessage.value = error
                _savedSuccessfully.value = false
                return@launch
            }

            // Si pasa la validacion, guardar
            val category = Category(
                name = nameTrimmed,
                categoryImageUri = imageUri.value
            )
            repository.insertCategory(category)

            // Limpiar campos y mensaje
            _name.value = ""
            _imageUri.value = null
            _errorMessage.value = ""
            _savedSuccessfully.value = true

        }
    }

    // resetear el flag tras la navegación
    fun resetSavedFlag() {
        _savedSuccessfully.value = false
    }
}