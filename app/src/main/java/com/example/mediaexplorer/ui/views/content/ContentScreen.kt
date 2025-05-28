package com.example.mediaexplorer.ui.views.content

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.data.entity.Content
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    navController: NavController,
    contentId: Int,
    categoryName: String,
    viewModel: ContentScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val content by viewModel.content.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadContent(contentId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
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
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Editar") },
                                    onClick = {
                                        // Navegación futura a edición
                                        showMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Eliminar") },
                                    onClick = {
                                        showDeleteDialog = true
                                        showMenu = false
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
    ) { innerPadding ->
        content?.let {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (categoryName) {
                    "Película" -> {
                        content?.let {
                            PeliculaCard(it)
                        }
                    }
                    "Serie" -> {
                        content?.let {
                            SerieCard(it)
                        }
                    }
                    "Anime" -> {
                        content?.let {
                            AnimeCard(it)
                        }
                    }
                    else -> {
                        content?.let {
                            OtroCard(it)
                        }
                    }
                }

            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro que quieres eliminar este contenido?") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deleteContent()
                        navController.popBackStack()
                    }
                    showDeleteDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun PeliculaCard(content: Content) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "Duración: ${content.duration ?: "N/A"} minutos"
    )
}

@Composable
fun GenericCard(imageUri: String?, name: String, information: String, extra: String) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (imageUri != null) {
                AsyncImage(
                    model = Uri.parse(imageUri),
                    contentDescription = name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(12f / 9f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }else {
                Image(
                    painter = painterResource(R.drawable.placeholder),
                    contentDescription = name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(12f / 9f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = "Sinopsis: \n$information",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = extra,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun SerieCard(content: Content) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "Capítulos: ${content.cantCap ?: "N/A"}"
    )
}

@Composable
fun AnimeCard(content: Content) {
    val genero = content.typeGender ?: "Sin género"
    val caps = content.cantCap ?: "N/A"
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "Capítulos: $caps | Género: $genero"
    )
}

@Composable
fun OtroCard(content: Content) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "Tipo: ${content.typeContent ?: "Desconocido"}"
    )
}
