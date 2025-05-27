package com.example.mediaexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediaexplorer.ui.views.category.CreateCategoryScreen
import com.example.mediaexplorer.ui.views.category.CategoryScreen
import com.example.mediaexplorer.ui.views.HomeScreen
import com.example.mediaexplorer.ui.views.content.ContentEntryScreen
import com.example.mediaexplorer.ui.views.content.ContentScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object CreateCategorySc

@Serializable
data class CategorySc(val categoryId: Int, val categoryName: String)

@Serializable
data class CreateContentSc(val categoryId: Int)

@Serializable
data class ContentSc(val contentId: Int, val contentTitle: String)

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
                categoryName = args.categoryName
            )
        }
        composable<CreateContentSc> { backStackEntry ->
            val args = backStackEntry.toRoute<CreateContentSc>()
            ContentEntryScreen(navController = navController, categoryId = args.categoryId)
        }
        composable<ContentSc>{ backStackEntry ->
            val args = backStackEntry.toRoute<ContentSc>()
            ContentScreen(navController, contentId = args.contentId)
        }
    }
}

enum class TypeContent(val displayName: String) {
    PELICULA("Película"),
    SERIE("Serie"),
    ANIME("Anime"),
    OTRO("Otro")
}

