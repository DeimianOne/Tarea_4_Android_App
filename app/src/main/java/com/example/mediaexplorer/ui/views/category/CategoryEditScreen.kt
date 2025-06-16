package com.example.mediaexplorer.ui.views.category

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryEditScreen(
    navController: NavController,
    categoryId: Int,
    viewModel: CategoryEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val name by viewModel.name.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val updatedSuccessfully by viewModel.updatedSuccessfully.collectAsState()
    Log.d("CategoryEditScreen", "Nombre actual: $name")
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(categoryId) {
        viewModel.loadCategoryById(categoryId)
    }

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

    // cambiar pantalla solo si guardó con éxito
    LaunchedEffect(updatedSuccessfully) {
        if (updatedSuccessfully) {
            navController.popBackStack()
            viewModel.resetUpdatedFlag()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Editar Contenido",
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
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
                            coroutineScope.launch {
                                viewModel.updateCategory()
                            }
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                        ),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(text = stringResource(R.string.save_btn))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CategoryForm(
                name = name,
                onNameChange = viewModel::onNameChanged,
                onSubmit = viewModel::updateCategory,
                errorMessage = errorMessage
            )

            Spacer(modifier = Modifier.padding(8.dp))

            // Botón para seleccionar imagen
            Button(
                onClick = {
                    launcher.launch(arrayOf("image/*"))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(stringResource(R.string.select_icon))
            }

            // Mostrar imagen si se seleccionó
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                onClick = {
                    launcher.launch(arrayOf("image/*"))
                },
                shape = RoundedCornerShape(16.dp), // Bordes redondeados
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent // Fondo transparente de la tarjeta
                )
            ){
                if (imageUri != null) {
                    AsyncImage(
                        model = Uri.parse(imageUri),
                        contentDescription = "Icono categoría",
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    // Imagen por defecto desde drawable
                    Image(
                        painter = painterResource(id = R.drawable.otros2),
                        contentDescription = "Imagen por defecto",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}