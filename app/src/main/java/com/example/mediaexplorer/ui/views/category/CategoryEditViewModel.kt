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
import retrofit2.HttpException
import java.io.IOException

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

    private val _updatedSuccessfully = MutableStateFlow(false)
    val updatedSuccessfully: StateFlow<Boolean> = _updatedSuccessfully

    fun loadCategory(category: Category) {
        categoryId = category.id
        _name.value = category.name
        _imageUri.value = category.categoryImageUri
    }

    fun loadCategoryById(categoryId: Int) {
        viewModelScope.launch {
            try {
                val category = categoryRepository.getCategoryById(categoryId)
                category?.let { loadCategory(it) }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la categoría: ${e.localizedMessage ?: "Desconocido"}"
            }
        }
    }

    fun onNameChanged(value: String) { _name.value = value }
    fun onImageUriChanged(value: String?) { _imageUri.value = value }

    fun updateCategory() {
        viewModelScope.launch {
            try {
                val nameTrimmed = name.value.trim()
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
                    categoryImageUri = imageUri.value
                )
                categoryRepository.updateCategory(updatedCategory)
                _errorMessage.value = ""
                _updatedSuccessfully.value = true
            } catch (e: IOException) {
                _errorMessage.value = "No se pudo conectar con el servidor."
            } catch (e: HttpException) {
                _errorMessage.value = "Error del servidor: ${e.code()}"
            } catch (e: Exception) {
                _errorMessage.value = "Error inesperado: ${e.localizedMessage ?: "Desconocido"}"
            }
        }
    }

    fun resetUpdatedFlag() {
        _updatedSuccessfully.value = false
    }
}