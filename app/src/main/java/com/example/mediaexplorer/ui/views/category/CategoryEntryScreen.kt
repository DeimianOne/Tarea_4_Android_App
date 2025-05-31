package com.example.mediaexplorer.ui.views.category

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.views.AppViewModelProvider
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryScreen(
    navController: NavController,
    viewModel: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val name by viewModel.name.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    // Launcher para seleccionar imagen
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                // ✅ Guardar permiso persistente
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                viewModel.onImageUriChanged(it.toString())
            }
        }
    )

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
                        Text(text = stringResource(R.string.create_category), modifier = Modifier.padding(start = 20.dp))
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
                            viewModel.saveCategory()
                            navController.popBackStack()
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
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
                onSubmit = viewModel::saveCategory,
                errorMessage = errorMessage
            )

            Spacer(modifier = Modifier.padding(8.dp))

            // Botón para seleccionar imagen
            Button(
                onClick = {
                    launcher.launch(arrayOf("image/*"))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Seleccionar Ícono")
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
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.otros2),
                        contentDescription = "Imagen por defecto",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

        }
    }
}

// AQUI MODIFICAR PARA EL FORMULARIO
@Composable
fun CategoryForm(
    name: String,
    onNameChange: (String) -> Unit,
    onSubmit: () -> Unit,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = stringResource(R.string.category_name) + " categoría") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
