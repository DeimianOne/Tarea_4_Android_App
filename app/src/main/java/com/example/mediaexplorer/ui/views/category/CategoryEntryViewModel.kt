package com.example.mediaexplorer.ui.views.category

import android.util.Log
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
import retrofit2.HttpException
import java.io.IOException

class CategoryEntryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    val categories: StateFlow<List<Category>> = repository.getAllCategoriesStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _savedSuccessfully = MutableStateFlow(false)
    val savedSuccessfully: StateFlow<Boolean> = _savedSuccessfully

    fun onNameChanged(newName: String) {
        _name.value = newName
        Log.d("CategoryEntryVM", "Nombre cambiado a: $newName")
    }

    fun onImageUriChanged(newUri: String?) {
        _imageUri.value = newUri
        Log.d("CategoryEntryVM", "URI de imagen actualizada: $newUri")
    }


    fun saveCategory() {
        viewModelScope.launch {
            try {
                val nameTrimmed = name.value.trim()
                val existing = repository.getAllCategoriesStream().first()
                val error = validateCategoryName(nameTrimmed, existing)

                if (error != null) {
                    Log.w("CategoryEntryVM", "Validación fallida: $error")
                    _errorMessage.value = error
                    _savedSuccessfully.value = false
                    return@launch
                }

                val category = Category(name = nameTrimmed, categoryImageUri = imageUri.value)
                repository.insertCategory(category)

                Log.d("CategoryEntryVM", "Categoría insertada correctamente: ${category.name}")

                _name.value = ""
                _imageUri.value = null
                _errorMessage.value = ""
                _savedSuccessfully.value = true

            } catch (e: IOException) {
                Log.e("CategoryEntryVM", "Sin conexión al servidor: ${e.message}")
                _errorMessage.value = "No se pudo conectar con el servidor. Verifica tu conexión a internet."
            } catch (e: HttpException) {
                Log.e("CategoryEntryVM", "Error del servidor: ${e.code()}")
                _errorMessage.value = "Error del servidor: ${e.code()}"
            } catch (e: Exception) {
                Log.e("CategoryEntryVM", "Error inesperado: ${e.localizedMessage}")
                _errorMessage.value = "Error inesperado: ${e.localizedMessage ?: "Desconocido"}"
            }
        }
    }

    fun resetSavedFlag() {
        _savedSuccessfully.value = false
        Log.d("CategoryEntryVM", "Bandera de guardado reiniciada")
    }
}