package com.example.aminormusic.viewmodel

import CollapsedMusicPlayer
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
import com.example.aminormusic.ui.screens.ProfileScreen
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

    Scaffold(
        topBar = { SuperTopAppBar(navController) },
        bottomBar = { SuperBottomAppBar(navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    Modifier.fillMaxSize()
                ) {
                    composable("home") { HomeScreen() }
                    composable("search") {
                        // Pass the searchViewModel to SearchScreen
                        SearchScreen(searchViewModel = searchViewModel)
                    }
                    composable("wishlist") { WishlistScreen() }
                    composable("profile") { ProfileScreen() }
                    composable("settings") { SettingsScreen() }
                }

                // Display the collapsed music player if there is a currently playing track
                if (results?.data?.isNotEmpty() == true) {
                    CollapsedMusicPlayer(
                        albumArtUrl = results!!.data[currentPlayingIndex].album.cover_small,
                        title = results!!.data[currentPlayingIndex].title,
                        artistName = results!!.data[currentPlayingIndex].artist.name,
                        isPlaying = isPlaying,
                        onPlayPauseClick = {
                            if (isPlaying) searchViewModel.pauseMusic() else searchViewModel.resumeMusic()
                        },
                        onPreviousClick = {searchViewModel.playPreviousSong()},
                        onNextClick = {searchViewModel.playNextSong()}
                    )
                }
            }
        }
    )
}