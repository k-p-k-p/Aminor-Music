package com.example.aminormusic.viewmodel

import SearchScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import  com.example.aminormusic.ui.components.SuperTopAppBar
import com.example.aminormusic.ui.components.SuperBottomAppBar
import com.example.aminormusic.ui.screens.HomeScreen
import com.example.aminormusic.ui.screens.ProfileScreen
//import com.example.aminormusic.ui.screens.SearchScreen
import com.example.aminormusic.ui.screens.SettingsScreen
import com.example.aminormusic.ui.screens.WishlistScreen


@Composable
fun MainViewModel() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { SuperTopAppBar(navController) },
        bottomBar = { SuperBottomAppBar(navController) },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("search") { SearchScreen() }
                composable("wishlist") { WishlistScreen() }
                composable("profile") { ProfileScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    )
}

