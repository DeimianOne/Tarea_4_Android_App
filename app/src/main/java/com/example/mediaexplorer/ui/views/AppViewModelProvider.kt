package com.example.mediaexplorer.ui.views

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mediaexplorer.MediaExplorerApplication
import com.example.mediaexplorer.ui.views.category.CategoryEntryViewModel
import com.example.mediaexplorer.ui.views.category.CategoryScreenViewModel
import com.example.mediaexplorer.ui.views.content.ContentEntryViewModel


/**
 * Este objeto contiene una fábrica centralizada de ViewModels,
 * lo que permite inicializarlos en Compose u otras capas con acceso
 * a Application, Repositories, SavedStateHandle, etc.
 */
object AppViewModelProvider {

    /**
     * ViewModelFactory compartida que proporciona instancias de todos los ViewModels usados en la app.
     */
    val Factory = viewModelFactory {

        /**
         * Inicializador para el ViewModel de ingreso de categorías.
         * Aquí se obtiene el repositorio desde el contenedor de dependencias (AppContainer).
         */
        initializer {
            CategoryEntryViewModel(
                mediaExplorerApplication().container.categoryRepository
            )
        }

        initializer {
            ContentEntryViewModel(
                mediaExplorerApplication().container.contentRepository,
                mediaExplorerApplication().container.categoryRepository
            )
        }

        initializer {
            CategoryScreenViewModel(
                mediaExplorerApplication().container.contentRepository
            )
        }
        initializer {
            InitialLoadViewModel(
                mediaExplorerApplication().container.contentRepository,
                mediaExplorerApplication().container.categoryRepository
            )
        }


        // Aquí podrías añadir otros ViewModels si los necesitas, como por ejemplo:
        // initializer {
        //     ContentEntryViewModel(
        //         mediaExplorerApplication().container.contentRepository
        //     )
        // }

    }
}

/**
 * Función de extensión para acceder a MediaExplorerApplication desde AndroidViewModelFactory.
 * Permite obtener acceso al contenedor de dependencias (AppContainer).
 */
fun CreationExtras.mediaExplorerApplication(): MediaExplorerApplication =
    (this[APPLICATION_KEY] as MediaExplorerApplication)
