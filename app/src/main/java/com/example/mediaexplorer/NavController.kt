package com.example.mediaexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mediaexplorer.ui.views.HomeScreen
import com.example.mediaexplorer.ui.views.FormCreateScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object FormCreate

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home){
        composable<Home>{
            HomeScreen(navController = navController)
        }
        composable<FormCreate>{
            FormCreateScreen(navController = navController)
        }
    }
}

enum class TypeContent(val displayName: String) {
    PELICULA("Película"),
    SERIE("Serie"),
    ANIME("Anime"),
    OTRO("Otro")
}
open class Content(val id:Int, val name:String, val information:String,val category:TypeContent)
class Movies(id:Int, name:String, information:String,category:TypeContent, val duration:Int): Content(id, name, information, category)
class Series(id:Int, name:String, information:String,category:TypeContent, val cantCap:Int): Content(id, name, information, category)
class Anime(id:Int, name:String, information:String,category:TypeContent, val cantCap:Int, typeGender:String): Content(id, name, information, category)
class OtherContent(id:Int, name:String, information:String,category:TypeContent, val typeContent:String): Content(id, name, information, category)

/*  PLANTILLA PARA PANTALLA DE SINOPSIS DE CADA TARJETA

*     Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Button(
                        onClick = {
                            val popped = navController.popBackStack()
                            if (!popped) navController.navigate(Home)
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {

                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        )
        { }
    }
* */