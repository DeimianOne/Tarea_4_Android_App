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
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mediaexplorer.EditCategorySc
import com.example.mediaexplorer.data.entity.Category
import com.example.mediaexplorer.ui.views.category.CategoryScreenViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Info




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    categoryEntryViewModel: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    categoryScreenViewModel: CategoryScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val categories by categoryEntryViewModel.categories.collectAsState(initial = emptyList())
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    var showHelpDialog by remember { mutableStateOf(false) }

        Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.media_explorer),
                            contentDescription = "logo mediaexplorer",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .height(50.dp)
                                .padding(horizontal = 8.dp)
                        )
                        // Icono de ayuda
                        Icon(
                            painter = painterResource(id = R.drawable.interrogacion), // tu imagen en drawable
                            contentDescription = "Ayuda",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(28.dp)
                                .clickable {
                                    showHelpDialog = true
                                },
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
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
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            CategoryCardList(
                categories,
                navController,
                onLongPress = { category ->
                    selectedCategory = category
                    showSheet = true
                }
            )
        }
    }

    // Popup de Ayuda
    PopupHelpDialog(
        showDialog = showHelpDialog,
        onDismiss = { showHelpDialog = false }
    )

    if (showSheet && selectedCategory != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Button(
                    onClick = {
                        navController.navigate(EditCategorySc(selectedCategory!!.id))
                        showSheet = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    )
                ) {
                    Icon(Icons.Filled.Edit, "Floating action button.")
                    Text(stringResource(R.string.edit_category))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        showDeleteDialog = true
                        showSheet = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    )

                ) {
                    Icon(Icons.Filled.Delete, "Floating action button.")
                    Text(stringResource(R.string.delete_category))
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.confirm_delete)) },
            text = { Text(stringResource(R.string.question_delete)) },
            confirmButton = {
                TextButton(onClick = {
                    selectedCategory?.let {
                        coroutineScope.launch {
                            categoryScreenViewModel.deleteCategory(it)
                            showDeleteDialog = false
                        }
                    }
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
fun CategoryCardList(
    categories: List<Category>,
    navController: NavHostController,
    onLongPress: (Category) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(categories) { category ->
            CategoryCardItem(category, navController, onLongPress)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryCardItem(
    category: Category,
    navController: NavHostController,
    onLongPress: (Category) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .combinedClickable(
                onClick = {
                    navController.navigate(CategorySc(category.id, category.name, category.categoryImageUri.toString()))
                },
                onLongClick = {
                    onLongPress(category)
                }
            ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        ),
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
                Image(
                    painter = painterResource(R.drawable.otros2),
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




/**
 * Muestra un popup de ayuda con un título y un mensaje explicativo.
 *
 * @param showDialog Estado para controlar si el diálogo se muestra o no.
 * @param onDismiss Función que se llama cuando el usuario cierra el diálogo.
 */
@Composable
fun PopupHelpDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Ayuda",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Bienvenido a Media Explorer. Aquí puedes crear y gestionar tus categorías de contenido multimedia.\n\n" +
                            "• Para crear una nueva categoría, presiona el botón '+' en la parte inferior.\n" +
                            "• Haz click en una categoría para ver su contenido.\n" +
                            "• Mantén presionado para editar o eliminar una categoría.\n\n" +
                            "¡Esperamos que disfrutes usando la aplicación!\n\n\n"+
                            "Desarrollado por estudiantes de la UCSC: Pablo, Damián.\nCréditos a nuestro profesor B.\n\n\n© 2025. Todos los derechos reservados."
                    ,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        )
    }
}