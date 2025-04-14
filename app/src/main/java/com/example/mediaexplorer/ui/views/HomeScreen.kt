package com.example.mediaexplorer.ui.views

import android.graphics.Movie
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediaexplorer.FormCreate
import com.example.mediaexplorer.Movies
import com.example.mediaexplorer.R
import com.example.mediaexplorer.SecondPage
import com.example.mediaexplorer.TypeContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, listMovies: MutableList<Movies>) {
    val listMovies = addPredeter(listMovies)
    var selected by remember { mutableIntStateOf(0) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("MediaExplorer", color = MaterialTheme.colorScheme.onSurface) },
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
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ){

            Text(
                text = "Popular en MediaExplorer",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .align(Alignment.Start)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listMovies) { movie ->
                    MovieCard(navController, movie)
                }
            }

        }
    }
}

@Composable
fun MovieCard(navController: NavHostController, movie: Movies) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(bottom = 8.dp)
    ){

        Card(
            onClick = {
                navController.navigate(SecondPage(movie.id))
            },
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ){
            Image(
                painter = painterResource(id = movie.imageResId),
                contentDescription = movie.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(
                text = movie.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }
}
@Composable
fun addPredeter(listMovies: MutableList<Movies>): MutableList<Movies> {
    val ironMan = Movies(1, stringResource(id = R.string.IronMan), stringResource(id = R.string.IronManSinopsis), TypeContent.PELICULA, R.drawable.iron_man, R.string.IronManDuracion)
    val ironMan2 = Movies(2, stringResource(id = R.string.IronMan2), stringResource(id = R.string.IronMan2Sinopsis), TypeContent.PELICULA, R.drawable.iron_man_2, R.string.IronMan2Duracion)
    val ironMan3 = Movies(3, stringResource(id = R.string.IronMan3), stringResource(id = R.string.IronMan3Sinopsis), TypeContent.PELICULA, R.drawable.iron_man_3, R.string.IronMan3Duracion)
    val spiderManUnNuevoUniverso = Movies(4, stringResource(id = R.string.SpiderManUnNuevoUniverso), stringResource(id = R.string.SpiderManUnNuevoUniversoSinopsis), TypeContent.PELICULA, R.drawable.spider_man_into_the_spider_verse, R.string.SpiderManUnNuevoUniversoDuracion)
    val spiderManAcrosTheSpiderVerse = Movies(5, stringResource(id = R.string.SpiderManAcrosTheSpiderVerse), stringResource(id = R.string.SpiderManAcrosTheSpiderVerseSinopsis), TypeContent.PELICULA, R.drawable.spider_man_across_the_spider_verse, R.string.SpiderManAcrosTheSpiderVerseDuracion)
    val the_avengers = Movies(6, stringResource(id = R.string.TheAvengers), stringResource(id = R.string.TheAvengersSinopsis), TypeContent.PELICULA, R.drawable.the_avengers, R.string.TheAvengersDuracion)
    val avengersAgeOfUltron = Movies(7, stringResource(id = R.string.AvengersAgeOfUltron), stringResource(id = R.string.AvengersAgeOfUltronSinopsis), TypeContent.PELICULA, R.drawable.avengers_age_of_ultron_the_avengers_2, R.string.AvengersAgeOfUltronDuracion)
    val avengersInfinityWar = Movies(8, stringResource(id = R.string.AvengersInfinityWar), stringResource(id = R.string.AvengersInfinityWarSinopsis), TypeContent.PELICULA, R.drawable.avengers_infinity_war, R.string.AvengersInfinityWarDuracion)
    val avengersEndgame = Movies(9, stringResource(id = R.string.AvengersEndgame), stringResource(id = R.string.AvengersEndgameSinopsis), TypeContent.PELICULA, R.drawable.avengers_endgame, R.string.AvengersEndgameDuracion)

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