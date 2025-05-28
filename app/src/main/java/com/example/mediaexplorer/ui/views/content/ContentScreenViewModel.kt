package com.example.mediaexplorer.ui.views.content

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

    private val _content = MutableStateFlow<Content?>(null)
    val content: StateFlow<Content?> = _content

    fun loadContent(contentId: Int) {
        viewModelScope.launch {
            contentRepository.getContentStream(contentId).collect { loaded ->
                _content.value = loaded
            }
        }
    }

    fun deleteContent() {
        viewModelScope.launch {
            _content.value?.let {
                contentRepository.deleteContent(it)
            }
        }
    }
}
