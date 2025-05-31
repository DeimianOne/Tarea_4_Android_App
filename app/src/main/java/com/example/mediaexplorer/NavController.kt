package com.example.mediaexplorer

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediaexplorer.ui.views.category.CreateCategoryScreen
import com.example.mediaexplorer.ui.views.category.CategoryScreen
import com.example.mediaexplorer.ui.views.HomeScreen
import com.example.mediaexplorer.ui.views.category.CategoryEditScreen
import com.example.mediaexplorer.ui.views.content.ContentEditScreen
import com.example.mediaexplorer.ui.views.content.ContentEntryScreen
import com.example.mediaexplorer.ui.views.content.ContentScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object CreateCategorySc

@Serializable
data class CategorySc(val categoryId: Int, val categoryName: String, val categoryImageUri: String)

@Serializable
data class EditCategorySc(val categoryId: Int)

@Serializable
data class CreateContentSc(val categoryId: Int)

@Serializable
data class ContentSc(val contentId: Int, val categoryName: String)

@Serializable
data class EditContentSc(val contentId: Int)

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Home>{
            HomeScreen(navController = navController)
        }
        composable<CreateCategorySc> {
            CreateCategoryScreen(navController = navController)
        }
        composable<CategorySc> { backStackEntry ->
            val args = backStackEntry.toRoute<CategorySc>()
            CategoryScreen(
                navController = navController,
                categoryId = args.categoryId,
                categoryName = args.categoryName,
                categoryUri = args.categoryImageUri
            )
        }
        composable<EditCategorySc> { backStackEntry ->
            val args = backStackEntry.toRoute<EditCategorySc>()
            CategoryEditScreen(
                navController = navController,
                categoryId = args.categoryId,
            )
        }
        composable<CreateContentSc> { backStackEntry ->
            val args = backStackEntry.toRoute<CreateContentSc>()
            ContentEntryScreen(navController = navController, categoryId = args.categoryId)
        }
        composable<ContentSc> { backStackEntry ->
            val args = backStackEntry.toRoute<ContentSc>()
            ContentScreen(
                navController = navController,
                contentId = args.contentId,
                categoryName = args.categoryName
            )
        }
        composable<EditContentSc> { backStackEntry ->
            val args = backStackEntry.toRoute<EditContentSc>()
            ContentEditScreen(
                navController = navController,
                contentId = args.contentId,
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}

enum class TypeContent(val displayName: String) {
    PELICULA("Película"),
    SERIE("Serie"),
    ANIME("Anime"),
    OTRO("Otro")
}

