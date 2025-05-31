package com.example.mediaexplorer.ui.views.content

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.ui.views.AppViewModelProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentEditScreen(
    navController: NavHostController,
    contentId: Int,
    viewModel: ContentEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onSaveSuccess: () -> Unit,
) {
    val name by viewModel.name.collectAsState()
    val information by viewModel.information.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val cantCap by viewModel.cantCap.collectAsState()
    val typeGenre by viewModel.typeGenre.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LaunchedEffect(contentId) {
        viewModel.loadContentById(contentId)
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
                            text = "Editar Contenido",
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
                            viewModel.updateContent()
                            onSaveSuccess()
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("Guardar Cambios")
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

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
        }
    }
}
