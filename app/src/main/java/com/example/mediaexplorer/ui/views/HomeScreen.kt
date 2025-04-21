package com.example.mediaexplorer.ui.views

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediaexplorer.AnimeSc
import com.example.mediaexplorer.CardCategory
import com.example.mediaexplorer.CreateCategorySc
import com.example.mediaexplorer.CustomCategorySc
import com.example.mediaexplorer.MovieSc
import com.example.mediaexplorer.R
import com.example.mediaexplorer.SerieSc
import com.example.mediaexplorer.TypeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, category: MutableList<CardCategory>) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name), color = MaterialTheme.colorScheme.onSurface) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    navController.navigate(CreateCategorySc)
                },
                modifier = Modifier.padding(end = 6.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                    Text(text = stringResource(R.string.create_category))
                }
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
                text = stringResource(R.string.app_categories),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .align(Alignment.Start)
            )
            CategoryCard(navController, category)
        }
    }
}

@Composable
fun CategoryCard(navController: NavHostController,category:  MutableList<CardCategory>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(category) {
            Card(
                onClick = {
                    when(it.name){
                        TypeContent.PELICULA.displayName -> navController.navigate(MovieSc)

                        TypeContent.SERIE.displayName -> navController.navigate(SerieSc)

                        TypeContent.ANIME.displayName -> navController.navigate(AnimeSc)

                        else -> navController.navigate(CustomCategorySc(it.name))
                    }
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier
                    .padding(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Icon(
                        painter = painterResource(it.image ?: R.drawable.otros),
                        contentDescription = "Account Box",
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = it.name,
                        color = Color.White,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}




