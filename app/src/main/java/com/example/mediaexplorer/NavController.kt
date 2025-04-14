package com.example.mediaexplorer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediaexplorer.ui.views.HomeScreen
import com.example.mediaexplorer.ui.views.FormCreateScreen
import com.example.mediaexplorer.ui.views.SecondScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object FormCreate

@Serializable
data class SecondPage(val id: Int)

/*CREACION DE CLASES*/
open class Content(val id:Int, val name:String, val information:String,val category:TypeContent, val imageResId: Int )
class Movies(id:Int, name:String, information:String, category:TypeContent, imageResId: Int, val duration:Int): Content(id, name, information, category, imageResId)
class Series(id:Int, name:String, information:String,category:TypeContent, imageResId: Int , val cantCap:Int): Content(id, name, information, category, imageResId)
class Anime(id:Int, name:String, information:String,category:TypeContent, imageResId: Int , val cantCap:Int, typeGender:String): Content(id, name, information, category, imageResId)
class OtherContent(id:Int, name:String, information:String,category:TypeContent, imageResId: Int , val typeContent:String): Content(id, name, information, category, imageResId)

/*CONTENIDO PREDETERMINADO*/

//var ListMovies:MutableList<Movies> = mutableListOf()
var ListSeries:MutableList<Series> = mutableListOf()
var ListAnimes:MutableList<Anime> = mutableListOf()
var ListOtherContent:MutableList<OtherContent> = mutableListOf()



@Composable
fun Navigation(){
    var ListMovies = remember { mutableStateListOf<Movies>() }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home){
        composable<Home>{
            HomeScreen(navController = navController, listMovies = ListMovies)
        }
        composable<FormCreate>{
            FormCreateScreen(navController = navController)
        }
        composable<SecondPage>{ backStackEntry ->
            val args = backStackEntry.toRoute<SecondPage>()
            SecondScreen(navController, args.id, ListMovies)
        }
    }
}

enum class TypeContent(val displayName: String) {
    PELICULA("Película"),
    SERIE("Serie"),
    ANIME("Anime"),
    OTRO("Otro")
}