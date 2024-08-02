package com.example.aminormusic.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SuperBottomAppBar(navController: NavController) {
    BottomAppBar {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(Icons.Default.Home, contentDescription = "Home")
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate("profile") }) {
            Icon(Icons.Default.Person, contentDescription = "Profile")
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate("WishList") }) {
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favourites")
        }
    }
}
