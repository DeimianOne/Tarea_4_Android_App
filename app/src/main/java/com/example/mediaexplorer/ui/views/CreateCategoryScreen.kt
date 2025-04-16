package com.example.mediaexplorer.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mediaexplorer.CardCategory
import com.example.mediaexplorer.TypeContent

@Composable
fun CreateCategoryScreen(navController: NavController, categoryList: SnapshotStateList<CardCategory>){
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Crear nueva categoria", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Nombre categoria")},
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

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
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Guardar categoria")
        }
    }
}


//if (name.isNotBlank() && categoryList.none {it.name.equals(name, ignoreCase = true)}) {
//    val newCategory = CardCategory(
//        type = TypeContent.OTRO,
//        name = name, //name del input al del objeto
//        image = null,
//    )
//    categoryList.add(newCategory)
//    navController.popBackStack()
//}