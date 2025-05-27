package com.example.mediaexplorer.ui.views

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.R
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InitialLoadViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun loadInitialData(context: Context) {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("mediaexplorer_prefs", Context.MODE_PRIVATE)
            val alreadyLoaded = prefs.getBoolean("initial_data_loaded", false)
            if (alreadyLoaded) return@launch

            val existingCategories = categoryRepository.getAllCategoriesStream().first()
            val categoryExists = existingCategories.any { it.name == "Película" }

            if (!categoryExists) {
                val pkgC = context.packageName
                categoryRepository.insertCategory(
                    Category(name = "Película", categoryImageUri = "android.resource://$pkgC/${R.drawable.pelicula}")
                )
            }
            if (!categoryExists) {
                val pkgC = context.packageName
                categoryRepository.insertCategory(
                    Category(name = "Serie", categoryImageUri = "android.resource://$pkgC/${R.drawable.series}")
                )
            }
            if (!categoryExists) {
                val pkgC = context.packageName
                categoryRepository.insertCategory(
                    Category(name = "Anime", categoryImageUri = "android.resource://$pkgC/${R.drawable.anime1}")
                )
            }

            val existingContents = contentRepository.getContentsByCategoryStream("Película").first()
            if (existingContents.isEmpty()) {
                val res = context.resources
                val pkg = context.packageName

                val contents = listOf(
                    Content(
                        name = res.getString(R.string.IronMan),
                        information = res.getString(R.string.IronManSinopsis),
                        categoryName = "Película",
                        contentImageUri = "android.resource://$pkg/${R.drawable.iron_man}",
                        duration = res.getString(R.string.IronManDuracion).toInt()
                    ),
                    Content(
                        name = res.getString(R.string.IronMan2),
                        information = res.getString(R.string.IronMan2Sinopsis),
                        categoryName = "Película",
                        contentImageUri = "android.resource://$pkg/${R.drawable.iron_man_2}",
                        duration = res.getString(R.string.IronMan2Duracion).toInt()
                    )
                )

                contents.forEach { contentRepository.insertContent(it) }
            }

            prefs.edit().putBoolean("initial_data_loaded", true).apply()
        }
    }

}
