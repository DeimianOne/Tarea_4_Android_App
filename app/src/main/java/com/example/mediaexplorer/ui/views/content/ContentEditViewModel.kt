package com.example.mediaexplorer.ui.views.content

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContentEditViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val TAG = "ContentEditVM"

    private var contentId: Int? = null

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _information = MutableStateFlow("")
    val information: StateFlow<String> = _information

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    private val _duration = MutableStateFlow("")
    val duration: StateFlow<String> = _duration

    private val _cantCap = MutableStateFlow("")
    val cantCap: StateFlow<String> = _cantCap

    private val _typeGenre = MutableStateFlow("")
    val typeGenre: StateFlow<String> = _typeGenre

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    init {
        viewModelScope.launch {
            try {
                categoryRepository.getAllCategoriesStream().collect {
                    _categories.value = it
                    Log.d(TAG, "Categorías cargadas: ${it.size}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar categorías: ${e.message}", e)
            }
        }
    }

    fun loadContent(content: Content) {
        contentId = content.id
        _name.value = content.name
        _information.value = content.information ?: ""
        _imageUri.value = content.contentImageUri
        _duration.value = content.duration?.toString() ?: ""
        _cantCap.value = content.cantCap?.toString() ?: ""
        _typeGenre.value = content.typeGender ?: ""
        _selectedCategory.value = _categories.value.find { it.name == content.categoryName }
        Log.d(TAG, "Contenido cargado: ${content.name}")
    }

    fun loadContentById(contentId: Int) {
        viewModelScope.launch {
            try {
                val content = contentRepository.getContentById(contentId)
                content?.let {
                    Log.d(TAG, "Contenido encontrado con ID $contentId: ${it.name}")
                    loadContent(it)
                } ?: Log.w(TAG, "No se encontró contenido con ID $contentId")
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar contenido: ${e.message}", e)
            }
        }
    }

    fun onNameChanged(value: String) { _name.value = value }
    fun onInformationChanged(value: String) { _information.value = value }
    fun onImageUriChanged(value: String) { _imageUri.value = value }
    fun onDurationChanged(value: String) { _duration.value = value }
    fun onCantCapChanged(value: String) { _cantCap.value = value }
    fun onTypeGenreChanged(value: String) { _typeGenre.value = value }
    fun onCategorySelected(category: Category) { _selectedCategory.value = category }

    fun updateContent() {
        viewModelScope.launch {
            try {
                val selectedCategoryName = _selectedCategory.value?.name ?: run {
                    Log.w(TAG, "No hay categoría seleccionada.")
                    return@launch
                }

                val isMovie = selectedCategoryName.equals("Película", ignoreCase = true)
                val isSeries = selectedCategoryName.equals("Serie", ignoreCase = true)
                val isAnime = selectedCategoryName.equals("Anime", ignoreCase = true)
                val isCustom = !(isMovie || isSeries || isAnime)

                val updatedContent = Content(
                    id = contentId ?: return@launch,
                    name = name.value,
                    information = information.value,
                    categoryName = selectedCategoryName,
                    contentImageUri = imageUri.value,
                    duration = if (isMovie) duration.value.toIntOrNull() else null,
                    cantCap = if (isSeries || isAnime) cantCap.value.toIntOrNull() else null,
                    typeGender = if (isAnime) typeGenre.value else null,
                    typeContent = if (isCustom) selectedCategoryName else null
                )
                contentRepository.updateContent(updatedContent)
                Log.d(TAG, "Contenido actualizado: ${updatedContent.name}")
            } catch (e: Exception) {
                Log.e(TAG, "Error al actualizar contenido: ${e.message}", e)
            }
        }
    }
}