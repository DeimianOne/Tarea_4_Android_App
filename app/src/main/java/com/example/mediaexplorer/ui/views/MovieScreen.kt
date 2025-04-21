package com.example.mediaexplorer.ui.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediaexplorer.FormCreate
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.Movies
import com.example.mediaexplorer.R
import com.example.mediaexplorer.TypeContent
import com.example.mediaexplorer.ui.components.ContentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(navController: NavHostController, movies: MutableList<Movies>) {
    addPredeter(movies)
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
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
                        Text(text = stringResource(R.string.app_name), color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(FormCreate)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            Text(
                text = stringResource(R.string.movies),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .align(Alignment.Start)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(movies){
                    ContentCard(navController, it)
                }
            }
        }
    }
}

@Composable
fun addPredeter(listMovies: MutableList<Movies>): MutableList<Movies> {
    val ironMan = Movies(1, stringResource(id = R.string.IronMan), stringResource(id = R.string.IronManSinopsis), TypeContent.PELICULA, R.drawable.iron_man, stringResource(R.string.IronManDuracion).toInt())
    val ironMan2 = Movies(2, stringResource(id = R.string.IronMan2), stringResource(id = R.string.IronMan2Sinopsis), TypeContent.PELICULA, R.drawable.iron_man_2, stringResource(R.string.IronMan2Duracion).toInt())
    val ironMan3 = Movies(3, stringResource(id = R.string.IronMan3), stringResource(id = R.string.IronMan3Sinopsis), TypeContent.PELICULA, R.drawable.iron_man_3, stringResource(R.string.IronMan3Duracion).toInt())
    val spiderManUnNuevoUniverso = Movies(4, stringResource(id = R.string.SpiderManUnNuevoUniverso), stringResource(id = R.string.SpiderManUnNuevoUniversoSinopsis), TypeContent.PELICULA, R.drawable.spider_man_into_the_spider_verse, stringResource(R.string.SpiderManUnNuevoUniversoDuracion).toInt())
    val spiderManAcrosTheSpiderVerse = Movies(5, stringResource(id = R.string.SpiderManAcrosTheSpiderVerse), stringResource(id = R.string.SpiderManAcrosTheSpiderVerseSinopsis), TypeContent.PELICULA, R.drawable.spider_man_across_the_spider_verse, stringResource(R.string.SpiderManAcrosTheSpiderVerseDuracion).toInt())
    val the_avengers = Movies(6, stringResource(id = R.string.TheAvengers), stringResource(id = R.string.TheAvengersSinopsis), TypeContent.PELICULA, R.drawable.the_avengers, stringResource(R.string.TheAvengersDuracion).toInt())
    val avengersAgeOfUltron = Movies(7, stringResource(id = R.string.AvengersAgeOfUltron), stringResource(id = R.string.AvengersAgeOfUltronSinopsis), TypeContent.PELICULA, R.drawable.avengers_age_of_ultron_the_avengers_2, stringResource(R.string.AvengersAgeOfUltronDuracion).toInt())
    val avengersInfinityWar = Movies(8, stringResource(id = R.string.AvengersInfinityWar), stringResource(id = R.string.AvengersInfinityWarSinopsis), TypeContent.PELICULA, R.drawable.avengers_infinity_war, stringResource(R.string.AvengersInfinityWarDuracion).toInt())
    val avengersEndgame = Movies(9, stringResource(id = R.string.AvengersEndgame), stringResource(id = R.string.AvengersEndgameSinopsis), TypeContent.PELICULA, R.drawable.avengers_endgame, stringResource(R.string.AvengersEndgameDuracion).toInt())

    listMovies.add(ironMan)
    listMovies.add(ironMan2)
    listMovies.add(ironMan3)
    listMovies.add(spiderManUnNuevoUniverso)
    listMovies.add(spiderManAcrosTheSpiderVerse)
    listMovies.add(the_avengers)
    listMovies.add(avengersAgeOfUltron)
    listMovies.add(avengersInfinityWar)
    listMovies.add(avengersEndgame)
    return listMovies
}