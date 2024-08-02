package com.example.aminormusic.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun SuperTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("Aminor Music") },
        actions = {
            IconButton(onClick = { navController.navigate("search") }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(Icons.Default.Person, contentDescription = "Profile")
            }
            IconButton(onClick = { navController.navigate("Settings") }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}