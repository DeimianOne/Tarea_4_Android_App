package com.example.mediaexplorer.ui.views.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CategoryScreenViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _contents = MutableStateFlow<List<Content>>(emptyList())
    val contents: StateFlow<List<Content>> = _contents

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesStream()
                .collect { _categories.value = it }
        }
    }

    fun loadContentByCategory(categoryName: String) {
        viewModelScope.launch {
            contentRepository.getContentsByCategoryStream(categoryName)
                .catch { e ->
                    when (e) {
                        is IOException -> Log.e("ContentVM", "Sin conexión: ${e.message}")
                        is HttpException -> Log.e("ContentVM", "Error HTTP: ${e.code()}")
                        else -> Log.e("ContentVM", "Error inesperado: ${e.message}")
                    }
                    _contents.value = emptyList() // Muestra fondo sin datos
                }
                .collect { list ->
                    _contents.value = list
                    Log.d("ContentVM", "Contenidos cargados: ${list.size}")
                }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                categoryRepository.deleteCategory(category)
            } catch (e: Exception) {
                // Podrías manejar errores si ocurre fallo al borrar
            }
        }
    }
    fun refreshCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesStream().collect {
                _categories.value = it
            }
        }
    }
    fun clearContents() {
        _contents.value = emptyList()
    }
}
