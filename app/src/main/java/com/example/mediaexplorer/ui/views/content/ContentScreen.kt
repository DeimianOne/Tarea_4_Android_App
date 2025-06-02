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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mediaexplorer.EditContentSc
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
                        Image(
                            painter = painterResource(R.drawable.media_explorer_letras),
                            contentDescription = "logo mediaexplorer",
                            contentScale = ContentScale.FillHeight, // Para que aproveche mejor el alto sin deformarse
                            modifier = Modifier
                                .height(60.dp) // Alto más grande
                                .padding(horizontal = 8.dp)
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.edit)) },
                                    onClick = {
                                        content?.let {
                                            navController.navigate(EditContentSc(contentId = it.id))
                                        }
                                        showMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.delete)) },
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
                            PeliculaCard(it,categoryName)
                        }
                    }
                    "Serie" -> {
                        content?.let {
                            SerieCard(it,categoryName)
                        }
                    }
                    "Anime" -> {
                        content?.let {
                            AnimeCard(it,categoryName)
                        }
                    }
                    else -> {
                        content?.let {
                            OtroCard(it,categoryName)
                        }
                    }
                }

            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.confirm_delete)) },
            text = { Text(stringResource(R.string.question_delete_content)) },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deleteContent()
                        navController.popBackStack()
                    }
                    showDeleteDialog = false
                }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }
}

@Composable
fun PeliculaCard(content: Content, categoryName: String) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "${content.duration ?: "N/A"} minutos",
        categoryName
    )
}

@Composable
fun GenericCard(imageUri: String?, name: String, information: String, extra: String, categoryName: String) {
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
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "$information",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            )
            when(categoryName){
                "Película"-> {
                    Text(
                        stringResource(R.string.duration),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = extra,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                "Serie" -> {
                    Text(
                        stringResource(R.string.chapters),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = extra,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                "Anime" -> {
                    Text(
                        stringResource(R.string.chapters),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    val con = extra.split(" ")
                    Text(
                        text = con[0],
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        stringResource(R.string.genero),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = con[1],
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
    }
}


@Composable
fun SerieCard(content: Content, categoryName: String) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "${content.cantCap ?: "N/A"}",
        categoryName
    )
}

@Composable
fun AnimeCard(content: Content, categoryName: String) {
    val genero = content.typeGender ?: "Sin género"
    val caps = content.cantCap ?: "N/A"
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "$caps $genero",
        categoryName
    )
}

@Composable
fun OtroCard(content: Content, categoryName: String) {
    GenericCard(
        imageUri = content.contentImageUri,
        name = content.name,
        information = content.information,
        extra = "Tipo: ${content.categoryName}",
        categoryName
    )
}
