package com.example.mediaexplorer.ui.views.content

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContentScreenViewModel(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val TAG = "ContentScreenVM"

    private val _content = MutableStateFlow<Content?>(null)
    val content: StateFlow<Content?> = _content

    fun loadContent(contentId: Int) {
        viewModelScope.launch {
            try {
                contentRepository.getContentStream(contentId).collect {
                    _content.value = it
                    Log.d(TAG, "Contenido cargado: ${it?.name ?: "nulo"}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar contenido: ${e.message}", e)
            }
        }
    }

    fun deleteContent() {
        viewModelScope.launch {
            try {
                _content.value?.let {
                    contentRepository.deleteContent(it)
                    Log.d(TAG, "Contenido eliminado: ${it.name}")
                } ?: Log.w(TAG, "No hay contenido cargado para eliminar.")
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar contenido: ${e.message}", e)
            }
        }
    }
}

