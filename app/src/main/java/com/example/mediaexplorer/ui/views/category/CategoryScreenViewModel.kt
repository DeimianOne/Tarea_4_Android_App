package com.example.mediaexplorer.ui.views.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryScreenViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    // Flujo interno mutable de la lista de contenidos
    private val _contents = MutableStateFlow<List<Content>>(emptyList())
    val contents: StateFlow<List<Content>> = _contents

    /**
     * Carga contenidos desde la base de datos según el nombre de categoría
     */
    fun loadContentByCategory(categoryName: String) {
        viewModelScope.launch {
            contentRepository.getContentsByCategoryStream(categoryName).collect { list ->
                _contents.value = list
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}
