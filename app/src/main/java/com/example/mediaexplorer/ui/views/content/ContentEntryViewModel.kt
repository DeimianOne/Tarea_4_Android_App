package com.example.mediaexplorer.ui.views.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContentEntryViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // Campos comunes
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _information = MutableStateFlow("")
    val information: StateFlow<String> = _information

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    // Campos específicos
    private val _duration = MutableStateFlow("")
    val duration: StateFlow<String> = _duration

    private val _cantCap = MutableStateFlow("")
    val cantCap: StateFlow<String> = _cantCap

    private val _typeGenre = MutableStateFlow("")
    val typeGenre: StateFlow<String> = _typeGenre

    // Lista de categorías obtenidas desde la base de datos
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    // Categoría actualmente seleccionada
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    init {
        // Al iniciar el ViewModel, suscribirse a los cambios de categorías
        viewModelScope.launch {
            categoryRepository.getAllCategoriesStream().collect { categoryList ->
                _categories.value = categoryList
            }
        }
    }

    // Funciones para actualizar cada campo desde la UI
    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }

    fun onNameChanged(value: String) {
        _name.value = value
    }

    fun onInformationChanged(value: String) {
        _information.value = value
    }

    fun onImageUriChanged(value: String) {
        _imageUri.value = value
    }

    fun onDurationChanged(value: String) {
        _duration.value = value
    }

    fun onCantCapChanged(value: String) {
        _cantCap.value = value
    }

    fun onTypeGenreChanged(value: String) {
        _typeGenre.value = value
    }

    /**
     * Guarda el contenido en la base de datos, considerando los campos según el tipo de categoría.
     */
    fun saveContent(categoryName: String) {
        viewModelScope.launch {
            val isMovie = categoryName.equals("Película", ignoreCase = true)
            val isSeries = categoryName.equals("Serie", ignoreCase = true)
            val isAnime = categoryName.equals("Anime", ignoreCase = true)
            val isCustom = !(isMovie || isSeries || isAnime)

            val newContent = Content(
                name = name.value,
                information = information.value,
                categoryName = categoryName, // Relación con la categoría
                contentImageUri = imageUri.value,
                duration = if (isMovie) duration.value.toIntOrNull() else null,
                cantCap = if (isSeries || isAnime) cantCap.value.toIntOrNull() else null,
                typeGender = if (isAnime) typeGenre.value else null,
                typeContent = if (isCustom) categoryName else null // solo para categorías personalizadas
            )

            contentRepository.insertContent(newContent)
        }
    }
}
