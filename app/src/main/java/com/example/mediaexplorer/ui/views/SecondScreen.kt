package com.example.mediaexplorer.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mediaexplorer.Anime
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.Movies
import com.example.mediaexplorer.OtherContent
import com.example.mediaexplorer.Series
import com.example.mediaexplorer.TypeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    navController: NavController,
    id: Int,
    category: String,
    ListMovies: SnapshotStateList<Movies>,
    ListSeries: SnapshotStateList<Series>,
    ListAnimes: SnapshotStateList<Anime>,
    ListCustomCont: SnapshotStateList<OtherContent>
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            when (category) {
                TypeContent.PELICULA.displayName -> {
                    val movie: Movies? = ListMovies.find { it.id == id }
                    Mcard(movie)
                }
                TypeContent.SERIE.displayName -> {
                    val serie: Series? = ListSeries.find { it.id == id }
                    Scard(serie)
                }
                TypeContent.ANIME.displayName -> {
                    val anime: Anime? = ListAnimes.find { it.id == id }
                    Acard(anime)
                }
                else -> {
                    val other: OtherContent? = ListCustomCont.find { it.id == id }
                    Ocard(other)
                }
            }
        }
    }
}


@Composable
fun Mcard(movie:Movies?){
    if (movie == null) Text("No hay contenido")
    else {
        Image(
            painter = painterResource(id = movie.imageResId),
            contentDescription = movie.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(12f / 9f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Sinopsis: \n ${movie.information}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )

                Text(
                    "Duración:      ${movie.duration} minutos",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Scard(serie:Series?){
    if (serie == null) Text("No hay contenido")
    else {
        Image(
            painter = painterResource(id = serie.imageResId),
            contentDescription = serie.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(12f / 9f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = serie.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Sinopsis: \n ${serie.information}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )

                Text(
                    "Cantidad de Capitulos:      ${serie.cantCap} capítulos",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Acard(anime:Anime?){
    if (anime == null) Text("No hay contenido")
    else {
        Image(
            painter = painterResource(id = anime.imageResId),
            contentDescription = anime.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(12f / 9f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = anime.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Sinopsis: \n ${anime.information}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
                Text(
                    "Genero:      ${anime.typeGender}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
                Text(
                    "Cantidad de Capitulos:      ${anime.cantCap}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Ocard(other:OtherContent?){
    if (other == null) Text("No hay contenido")
    else {
        Image(
            painter = painterResource(id = other.imageResId),
            contentDescription = other.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(12f / 9f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = other.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Sinopsis: \n ${other.information}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}
