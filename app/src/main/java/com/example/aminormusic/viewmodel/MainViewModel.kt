package com.example.aminormusic.viewmodel

import ExpandedMusicPlayer
import SearchScreen
//import SearchViewModel
import aminor.viewmodel.SearchViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aminormusic.ui.components.SuperTopAppBar
import com.example.aminormusic.ui.components.SuperBottomAppBar
import com.example.aminormusic.ui.screens.HomeScreen
import com.example.aminormusic.ui.screens.*
import com.example.aminormusic.ui.screens.SettingsScreen
import com.example.aminormusic.ui.screens.WishlistScreen

@Composable
fun MainViewModel() {
    val navController = rememberNavController()

    // Create an instance of SearchViewModel
    val searchViewModel: SearchViewModel = viewModel()

    // Collect necessary states from SearchViewModel
    val currentPlayingIndex by searchViewModel.currentPlayingIndex.collectAsState()
    val isPlaying by searchViewModel.isPlaying.collectAsState()
    val results by searchViewModel.results.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "welcome",
        Modifier.fillMaxSize()
    ) {
        composable("welcome"){ WelcomeScreen(navController)}
        composable("login") { LogInScreen(navController)}
        composable("signup"){ SignUpScreen(navController)}
        composable("home") { HomeScreen(navController) }
        composable("search") {
            // Pass the searchViewModel to SearchScreen
            SearchScreen(navController)
        }
        composable("wishlist") { WishlistScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}