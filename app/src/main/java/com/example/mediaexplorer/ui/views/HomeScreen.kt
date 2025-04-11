package com.example.mediaexplorer.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var selected by remember { mutableIntStateOf(0) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = Color(255, 255, 255, 255),
        topBar = {
            TopAppBar(
                title = {
                },
                colors = TopAppBarColors(
                    containerColor = Color(255, 255, 255, 255),
                    scrolledContainerColor = Color(255, 255, 255, 255),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(255, 255, 255, 255),
                modifier = Modifier.padding(64.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    FloatingActionButton(
                        onClick = { },
                        shape = CircleShape,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
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
        { }
    }
}