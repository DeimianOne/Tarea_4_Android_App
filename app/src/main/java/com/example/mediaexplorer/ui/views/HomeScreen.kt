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
import com.example.mediaexplorer.CreateCategorySc
import com.example.mediaexplorer.CategorySc
import com.example.mediaexplorer.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediaexplorer.ui.views.category.CategoryEntryViewModel
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.mediaexplorer.data.entity.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.media_explorer),
                        contentDescription = "logo mediaexplorer",
                        contentScale = ContentScale.FillHeight, // Para que aproveche mejor el alto sin deformarse
                        modifier = Modifier
                            .height(50.dp) // Alto más grande
                            .padding(horizontal = 8.dp)
                    )
                    //Text(text = stringResource(R.string.app_name), color = MaterialTheme.colorScheme.onSurface)
                        },
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
            CategoryCardList(categories, navController)
        }
    }
}

@Composable
fun CategoryCardList(
    categories: List<Category>,
    navController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(categories) { category ->
            CategoryCardItem(category, navController)
        }
    }
}

@Composable
fun CategoryCardItem(
    category: Category,
    navController: NavHostController
) {
    Card(
        onClick = {
            navController.navigate(CategorySc(category.id, category.name))
        },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        ),
        modifier = Modifier.padding(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (category.categoryImageUri != null) {
                AsyncImage(
                    model = Uri.parse(category.categoryImageUri),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.otros),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Text(
                text = category.name,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

/*
@Composable
fun CategoryCard(navController: NavHostController){
        items(category) {
            Card(
                onClick = {
                    navController.navigate(contentRoute(it.id, it.nombre))
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
*/