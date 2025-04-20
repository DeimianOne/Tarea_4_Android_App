package com.example.mediaexplorer.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mediaexplorer.Anime
import com.example.mediaexplorer.CardCategory
import com.example.mediaexplorer.Home
import com.example.mediaexplorer.Movies
import com.example.mediaexplorer.OtherContent
import com.example.mediaexplorer.R
import com.example.mediaexplorer.Series
import com.example.mediaexplorer.TypeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryScreen(navController: NavController, categoryList: SnapshotStateList<CardCategory>){
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
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
                        Text(
                            "Crear Categoria",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
                        )

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            errorMessage = ""
                            when {
                                name.isBlank() -> {
                                    errorMessage = "El nombre no puede estar vacío"
                                }
                                categoryList.any { it.name.equals(name, ignoreCase = true) } -> {
                                    errorMessage = "Ya existe una categoría con ese nombre"
                                }
                                else -> {
                                    val newCategory = CardCategory(
                                        type = TypeContent.OTRO,
                                        name = name, //name del input al del objeto
                                        image = null,
                                    )
                                    categoryList.add(newCategory)
                                    navController.popBackStack()
                                }
                            }
                        },
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Guardar categoria")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Nombre categoria")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.padding(16.dp))

        }
    }
}