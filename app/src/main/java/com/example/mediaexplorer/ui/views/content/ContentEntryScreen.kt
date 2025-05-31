package com.example.mediaexplorer.ui.views.content

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.ui.views.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEntryScreen(
    navController: NavHostController,
    categoryId: Int,
    viewModel: ContentEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val name by viewModel.name.collectAsState()
    val information by viewModel.information.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val cantCap by viewModel.cantCap.collectAsState()
    val typeGenre by viewModel.typeGenre.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    // Launcher para seleccionar categoria sin seleccionarla
    LaunchedEffect(categories, selectedCategory) {
        if (selectedCategory == null && categories.isNotEmpty()) {
            val initialCategory = categories.firstOrNull { it.id == categoryId }
            initialCategory?.let { viewModel.onCategorySelected(it) }
        }
    }
    // Launcher para seleccionar imagen desde almacenamiento
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                // Tomar permiso persistente
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.onImageUriChanged(it.toString())
            }
        }
    )

    val isFormValid by remember(name, information, cantCap, typeGenre, selectedCategory) {
        derivedStateOf {
            when (selectedCategory?.name) {
                "Película" -> name.isNotBlank() && information.isNotBlank() && duration.isNotBlank()
                "Serie" -> name.isNotBlank() && information.isNotBlank() && cantCap.isNotBlank()
                "Anime" -> name.isNotBlank() && information.isNotBlank() && cantCap.isNotBlank() && typeGenre.isNotBlank()
                else -> name.isNotBlank() && information.isNotBlank()
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                val popped = navController.popBackStack()
                                if (!popped) navController.navigate(Home)
                            },
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = stringResource(R.string.add_content),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            // Guardar contenido en la base de datos usando el nombre de la categoría seleccionada
                            selectedCategory?.let {
                                viewModel.saveContent(it.name)
                                navController.popBackStack()
                            }
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(text = stringResource(R.string.save_btn))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // o prueba con 32.dp si lo quieres más separado

            CategoryDropdown(categories, selectedCategory, viewModel::onCategorySelected)

            Spacer(modifier = Modifier.height(16.dp))

            CommonFields(name, information, viewModel::onNameChanged, viewModel::onInformationChanged)

            ConditionalFields(
                selectedCategory?.name,
                duration,
                cantCap,
                typeGenre,
                viewModel::onDurationChanged,
                viewModel::onCantCapChanged,
                viewModel::onTypeGenreChanged
            )

            ImagePickerSection(imageUri) {
                launcher.launch(arrayOf("image/*"))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryDropdown(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.border(1.dp, Color(0xFFD84040), RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(selectedCategory?.name ?: stringResource(R.string.select_category))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 120.dp, y = 0.dp)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CommonFields(
    name: String,
    information: String,
    onNameChange: (String) -> Unit,
    onInformationChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Nombre") },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
    )
    OutlinedTextField(
        value = information,
        onValueChange = onInformationChange,
        label = { Text("Descripción") },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
    )

}

@Composable
fun ConditionalFields(
    selectedCategory: String?,
    duration: String,
    cantCap: String,
    typeGenre: String,
    onDurationChange: (String) -> Unit,
    onCantCapChange: (String) -> Unit,
    onGenreChange: (String) -> Unit
) {
    when (selectedCategory) {
        "Película" -> {
            OutlinedTextField(
                value = duration,
                onValueChange = onDurationChange,
                label = { Text("Duración (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
        "Serie" -> {
            OutlinedTextField(
                value = cantCap,
                onValueChange = onCantCapChange,
                label = { Text("Cantidad de capítulos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
        "Anime" -> {
            OutlinedTextField(
                value = cantCap,
                onValueChange = onCantCapChange,
                label = { Text("Cantidad de capítulos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
            )
            OutlinedTextField(
                value = typeGenre,
                onValueChange = onGenreChange,
                label = { Text("Género") },
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}

@Composable
fun ImagePickerSection(
    imageUri: String?,
    onPickImage: () -> Unit
) {

    Button(
        onClick = onPickImage,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Seleccionar Imagen")
    }

    Spacer(modifier = Modifier.height(8.dp))
    Card(
        onClick = onPickImage,
        shape = RoundedCornerShape(16.dp), // Bordes redondeados
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Fondo transparente de la tarjeta
        )
    ){
        if (imageUri != null) {
            AsyncImage(
                model = Uri.parse(imageUri),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

