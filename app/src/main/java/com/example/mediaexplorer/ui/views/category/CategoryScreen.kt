package com.example.mediaexplorer.ui.views.category

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mediaexplorer.CreateContentSc
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.components.ContentCard
import com.example.mediaexplorer.ui.views.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryId: Int,
    categoryName: String,
    categoryUri: String,
    viewModel: CategoryScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val contents by viewModel.contents.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }
    // Cargar contenidos al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadContentByCategory(categoryName)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                viewModel.clearContents()
                                val popped = navController.popBackStack()
                                if (!popped) navController.navigate(Home)
                            },
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Image(
                            painter = painterResource(R.drawable.media_explorer_letras),
                            contentDescription = "logo mediaexplorer",
                            contentScale = ContentScale.FillHeight, // Para que aproveche mejor el alto sin deformarse
                            modifier = Modifier
                                .height(60.dp) // Alto más grande
                                .padding(horizontal = 8.dp)
                        )
                        /*
                        Text(
                            text = stringResource(R.string.app_name),
                            modifier = Modifier.padding(start = 20.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        */
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(CreateContentSc(categoryId))
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar contenido")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row (
                Modifier.align(Alignment.Start)
            ){
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                if (categoryUri != null) {
                    AsyncImage(
                        model = Uri.parse(categoryUri),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp).padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.otros),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }


            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(contents) { content ->
                    ContentCard(navController = navController, content = content)
                }
            }
        }
    }
}


