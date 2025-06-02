package com.example.mediaexplorer.ui.views.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.ui.components_utils.validateCategoryName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    // variable para comprobar si se guardo correctamente
    private val _updatedSuccessfully = MutableStateFlow(false)
    val updatedSuccessfully: StateFlow<Boolean> = _updatedSuccessfully

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

            // Aquí se usa el categoryId para que no se detecte como duplicado a sí misma
            val existing = categoryRepository.getAllCategoriesStream().first()
            val error = validateCategoryName(nameTrimmed, existing, categoryId)

            if (error != null) {
                _errorMessage.value = error
                _updatedSuccessfully.value = false
                return@launch
            }

            val updatedCategory = Category(
                id = categoryId ?: return@launch,
                name = nameTrimmed,
                categoryImageUri = imageUri.value,
            )
            categoryRepository.updateCategory(updatedCategory)
            // log para depuración
//            Log.d("CategoryEditViewModel", "Categoría actualizada: ID=${updatedCategory.id}, Nombre=${updatedCategory.name}")
            _errorMessage.value = ""
            _updatedSuccessfully.value = true
        }
    }

    // resetear el flag tras la navegación
    fun resetUpdatedFlag() {
        _updatedSuccessfully.value = false
    }
}