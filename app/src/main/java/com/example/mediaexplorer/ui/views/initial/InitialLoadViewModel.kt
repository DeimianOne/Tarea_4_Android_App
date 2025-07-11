package com.example.mediaexplorer.ui.views

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.data.repository.CategoryRepository
import com.example.mediaexplorer.data.repository.ContentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class InitialLoadViewModel(
    private val contentRepository: ContentRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val TAG = "InitialLoadVM"
    private val _initialLoadDone = MutableStateFlow(false)
    val initialLoadDone: StateFlow<Boolean> get() = _initialLoadDone

    fun loadInitialData(context: Context) {
        viewModelScope.launch {
            _initialLoadDone.value = false
            try {
                val prefs = context.getSharedPreferences("mediaexplorer_prefs", Context.MODE_PRIVATE)
                val alreadyLoaded = prefs.getBoolean("initial_data_loaded", false)
                if (alreadyLoaded) {
                    Log.d(TAG, "Los datos iniciales ya fueron cargados anteriormente.")
                    return@launch
                }

                val existingCategories = categoryRepository.getAllCategoriesStream().first()
                val existingCategoryNames = existingCategories.map { it.name }
                val pkg = context.packageName

                if ("Película" !in existingCategoryNames) {
                    categoryRepository.insertCategory(
                        Category(name = "Película", categoryImageUri = "android.resource://$pkg/${R.drawable.pelicula_ico}")
                    )
                    Log.d(TAG, "Categoría 'Película' insertada.")
                }
                if ("Serie" !in existingCategoryNames) {
                    categoryRepository.insertCategory(
                        Category(name = "Serie", categoryImageUri = "android.resource://$pkg/${R.drawable.serie_ico}")
                    )
                    Log.d(TAG, "Categoría 'Serie' insertada.")
                }
                if ("Anime" !in existingCategoryNames) {
                    categoryRepository.insertCategory(
                        Category(name = "Anime", categoryImageUri = "android.resource://$pkg/${R.drawable.anime_ico}")
                    )
                    Log.d(TAG, "Categoría 'Anime' insertada.")
                }

                val existingContents = categoryRepository.getCategoryById(1)

                if (existingContents?.name == "Película") {
                    val res = context.resources
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
                    contents.forEach {
                        contentRepository.insertContent(it)
                        Log.d(TAG, "Contenido '${it.name}' insertado.")
                    }
                }

                prefs.edit().putBoolean("initial_data_loaded", true).apply()
                Log.d(TAG, "Datos iniciales cargados correctamente.")
                _initialLoadDone.value = true


            } catch (e: IOException) {
                Log.e(TAG, "No hay conexión: ${e.message}", e)
            } catch (e: HttpException) {
                Log.e(TAG, "Error del servidor (${e.code()}): ${e.message()}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error inesperado: ${e.localizedMessage}", e)
            }finally {
                _initialLoadDone.value = true // <-- Asegura salida limpia
            }
        }
    }
}
