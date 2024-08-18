package com.example.aminormusic.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aminormusic.model.apicallerclasses.FetchData
import com.example.aminormusic.model.apidataclasses.Album
import com.example.aminormusic.model.apidataclasses.Artist
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MyApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color.White,
            onPrimary = Color.Black,
            surface = Color.White,
            onSurface = Color.Black
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home Icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Welcome back!")
                        }
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications Icon",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings Icon",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Account Icon",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    },
                )
            },
            bottomBar = {
                BottomAppBar(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite Icon")
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share Icon")
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainContent()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

@Composable
fun MainContent() {
    val scope = rememberCoroutineScope()
    var artists by remember { mutableStateOf<List<Artist>>(emptyList()) }
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }

    LaunchedEffect(Unit) {
        scope.launch {
            FetchData().fetchData { apiData ->
                if (apiData != null) {
                    artists = apiData.data.map { it.artist }
                    albums = apiData.data.map { it.album }
                } else {
                    artists = emptyList()
                    albums = emptyList()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Continue Listening", color = Color.White)
        ArtistRow(artists = artists, albums = albums)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your Top Mixes", color = Color.White)
        ArtistRow(artists = artists, albums = albums)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Based on your recent listening", color = Color.White)
        ArtistRow(artists = artists, albums = albums)
    }
}

@Composable
fun ArtistRow(artists: List<Artist>, albums: List<Album>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(artists.size) { index ->
            ArtistCard(album = albums[index])
        }
    }
}

@Composable
fun ArtistCard(album: Album) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(album.cover_medium)
                        .build()
                ),
                contentDescription = "Album Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = album.title,  // Display the album title here
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(4.dp)
            )
        }
    }
}
