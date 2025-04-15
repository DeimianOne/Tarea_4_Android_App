package com.example.mediaexplorer.ui.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediaexplorer.Home
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import com.example.mediaexplorer.CardCategory
import com.example.mediaexplorer.Movies
import com.example.mediaexplorer.TypeContent
import java.io.File
import java.io.FileOutputStream
import java.util.Locale.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCreateScreen(navController: NavHostController, category: MutableList<CardCategory>) {

    Scaffold(modifier = Modifier.fillMaxSize(),
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
                            "Agregue Contenido",
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
                        onClick = { },
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        )
        {
            Text(
                "¿Tipo de Contenido?",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre texto y botón

            var expanded by remember { mutableStateOf(false) }
            var typeSelec by remember { mutableStateOf<CardCategory?>(null) }

            Box {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD84040),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, // Fondo transparente
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        typeSelec?.name ?: "Selecciona una categoría"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 120.dp, y = 0.dp) // prueba valores según el ancho
                ) {
                    category.forEach { tipo ->
                        DropdownMenuItem(
                            text = {
                                Text(tipo.name)
                            },
                            onClick = {
                                typeSelec = tipo
                                expanded = false
                            }
                        )
                    }
                }
            }
            when (typeSelec?.type) {
                TypeContent.PELICULA ->{
                    var name by remember { mutableStateOf("")}
                    var information by remember { mutableStateOf("")}
                    var duration by remember { mutableStateOf("")}

                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = { Text("Nombre Pelicula")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = information,
                        onValueChange = {information = it},
                        label = { Text("Sinopsis")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = duration,
                        onValueChange = {duration = it},
                        label = { Text("Duración (min)")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    val context = LocalContext.current
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent(),
                        onResult = { uri ->
                            if (uri != null) {
                                val inputStream = context.contentResolver.openInputStream(uri)
                                val file = File(context.filesDir, "imagenes") // carpeta interna
                                if (!file.exists()) file.mkdirs()

                                val outputFile = File(file, "imagen_${System.currentTimeMillis()}.jpg")
                                val outputStream = FileOutputStream(outputFile)

                                inputStream?.copyTo(outputStream)

                                inputStream?.close()
                                outputStream.close()
                            }
                        }

                    )
                    Button(onClick = { launcher.launch("image/*") }) {
                        Text("Subir imagen")
                    }
                }

                TypeContent.SERIE -> {
                    var name by remember { mutableStateOf("")}
                    var information by remember { mutableStateOf("")}
                    var cantCap by remember { mutableStateOf("")}

                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = { Text("Nombre Serie")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = information,
                        onValueChange = {information = it},
                        label = { Text("Descripción")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = cantCap,
                        onValueChange = {cantCap = it},
                        label = { Text("Cantidad de Capítulos")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
                TypeContent.ANIME -> {
                    var name by remember { mutableStateOf("")}
                    var information by remember { mutableStateOf("")}
                    var cantCap by remember { mutableStateOf("")}
                    var typeGender by remember { mutableStateOf("")}

                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = { Text("Nombre Anime")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = information,
                        onValueChange = {information = it},
                        label = { Text("Descripción")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = cantCap,
                        onValueChange = {cantCap = it},
                        label = { Text("Cantidad de Capítulos")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    OutlinedTextField(
                        value = typeGender,
                        onValueChange = {typeGender = it},
                        label = { Text("Género Anime")},
                        modifier = Modifier.padding(10.dp),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
                else -> {

                }
            }

        }
    }
}

/*@Composable
fun DropMenu(typeSelec: TypeContent?) {
    when (typeSelec) {
        TypeContent.PELICULA ->{
            var name by remember { mutableStateOf("")}
            var information by remember { mutableStateOf("")}
            var duration by remember { mutableStateOf("")}

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Nombre Pelicula")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = information,
                onValueChange = {information = it},
                label = { Text("Sinopsis")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = duration,
                onValueChange = {duration = it},
                label = { Text("Duración (min)")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri ->
                    // Aquí recibes el URI de la imagen seleccionada
                    if (uri != null) {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val file = File(context.filesDir, "imagenes") // carpeta interna
                        if (!file.exists()) file.mkdirs()

                        val outputFile = File(file, "imagen_${System.currentTimeMillis()}.jpg")
                        val outputStream = FileOutputStream(outputFile)

                        inputStream?.copyTo(outputStream)

                        inputStream?.close()
                        outputStream.close()
                    }
                }
            )
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Subir imagen")
            }
        }

        TypeContent.SERIE -> {
            var name by remember { mutableStateOf("")}
            var information by remember { mutableStateOf("")}
            var cantCap by remember { mutableStateOf("")}

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Nombre Serie")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = information,
                onValueChange = {information = it},
                label = { Text("Descripción")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = cantCap,
                onValueChange = {cantCap = it},
                label = { Text("Cantidad de Capítulos")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
        }
        TypeContent.ANIME -> {
            var name by remember { mutableStateOf("")}
            var information by remember { mutableStateOf("")}
            var cantCap by remember { mutableStateOf("")}
            var typeGender by remember { mutableStateOf("")}

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Nombre Anime")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = information,
                onValueChange = {information = it},
                label = { Text("Descripción")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = cantCap,
                onValueChange = {cantCap = it},
                label = { Text("Cantidad de Capítulos")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
            OutlinedTextField(
                value = typeGender,
                onValueChange = {typeGender = it},
                label = { Text("Género Anime")},
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(color = Color.White)
            )
        }
        else -> {

        }
    }
}
*/