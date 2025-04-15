package com.example.mediaexplorer.ui.views

import android.graphics.Movie
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.Movies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavController, id: Int, ListMovies: MutableList<Movies>) {
    val movie: Movies? = ListMovies.find { it.id == id }

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
        {
            if (movie == null) {
                Text("Pagina no disponible")
            }
            else {
                Image(
                    painter = painterResource(id = movie.imageResId),
                    contentDescription = movie.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(12f / 9f) // Puedes ajustar a 4f / 3f, etc.
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
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ){
                    item {
                        Text("Sinopsis: \n ${movie.information}",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                    .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                        )

                        Text("Duración:      ${stringResource(movie.duration)} minutos",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                    .padding(start = 11.dp, top = 8.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}