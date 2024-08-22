import aminor.viewmodel.SearchViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.aminormusic.R
import com.example.aminormusic.ui.screens.HomeScreen
import com.example.aminormusic.ui.screens.WelcomeScreen

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchScreen(rememberNavController())
}

@Composable
fun SearchScreen(navController: NavHostController,searchViewModel: SearchViewModel = viewModel()
) {
    val query by searchViewModel.query.collectAsState()
    val results by searchViewModel.results.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val currentPlayingIndex by searchViewModel.currentPlayingIndex.collectAsState()
    val isPlaying by searchViewModel.isPlaying.collectAsState()
    val noResultsFound by searchViewModel.noResultsFound.collectAsState()
    val apiError by searchViewModel.apiError.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0e3135), // Start color
                            Color(0xFF0e3135), // Transition color (same as start to cover 70%)
                            Color(0xFF000000)  // End color
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY),
                        tileMode = TileMode.Clamp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Padding inside the Box
            )  {
                IconButton(
                    onClick = { navController.navigate("home") },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_previous),
                        contentDescription = null,
                        Modifier.size(30.dp),
                        tint = Color(0xFFFFFFFF)
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = query,
                    onValueChange = { searchViewModel.onQueryChanged(it) },
                    label = {
                        Text(
                            text = "Browse library",
                            color = Color(0xFFBEBEBE) // Set the desired color for the label
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { searchViewModel.search() },
                            modifier = Modifier.size(30.dp) // Set size of the icon
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    },
                    modifier = Modifier
                        .width(340.dp)
                        .height(59.dp)
                        .align(Alignment.CenterHorizontally), // Center align the text field horizontally
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(
                        color = Color(0xFFFFFFFF), // Set your desired text color here
                        fontSize = 16.sp // You can also set other text style properties here
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))


                if (isLoading) {
                    Text("Loading...", fontSize = 20.sp, color = Color.Gray)
                } else {
                    if (noResultsFound) {
                        Text("No results found.")
                    } else if (apiError) {
                        Text("API error.")
                    } else {
                        results?.let {
                            if (it.data.isNotEmpty()) {
                                // ...

                                ExpandedMusicPlayer(
                                    albumArtUrl = it.data[currentPlayingIndex].album.cover_big,
                                    title = it.data[currentPlayingIndex].title,
                                    artistName = it.data[currentPlayingIndex].artist.name,
                                    isPlaying = isPlaying,
                                    onPlayPauseClick = {
                                        if (isPlaying) searchViewModel.pauseMusic() else searchViewModel.resumeMusic()
                                    },
                                    onPreviousClick = { searchViewModel.playPreviousSong() },
                                    onNextClick = { searchViewModel.playNextSong() }
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                CollapsedMusicPlayer(
                                    albumArtUrl = it.data[currentPlayingIndex].album.cover_small,
                                    title = it.data[currentPlayingIndex].title,
                                    artistName = it.data[currentPlayingIndex].artist.name,
                                    isPlaying = isPlaying,
                                    onPlayPauseClick = {
                                        if (isPlaying) searchViewModel.pauseMusic() else searchViewModel.resumeMusic()
                                    },
                                    onPreviousClick = { searchViewModel.playPreviousSong() },
                                    onNextClick = { searchViewModel.playNextSong() }
                                )
                            } else {
                                Text(
                                    text = "No results found.",
                                    color = Color(0xFF999999),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )

                            }
                        } ?: Text(
                            text = "No results found.",
                            color = Color(0xFF999999),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )

                    }
                }
            }
        }
    }
}

        @Composable
        fun ExpandedMusicPlayer(
            albumArtUrl: String,
            title: String,
            artistName: String,
            isPlaying: Boolean,
            onPlayPauseClick: () -> Unit,
            onPreviousClick: () -> Unit,
            onNextClick: () -> Unit
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(albumArtUrl),
                    contentDescription = "Album Art",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = artistName,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    IconButton(onClick = onPreviousClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Previous",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onPlayPauseClick) {
                        Icon(
                            painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onNextClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.next_arrow_forward_svgrepo_com),
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        @Composable
        fun CollapsedMusicPlayer(
            albumArtUrl: String,
            title: String,
            artistName: String,
            isPlaying: Boolean,
            onPlayPauseClick: () -> Unit,
            onPreviousClick: () -> Unit,
            onNextClick: () -> Unit
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(albumArtUrl),
                    contentDescription = "Album Art",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = artistName,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = onPreviousClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_previous),
                        contentDescription = "Previous",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
                IconButton(onClick = onPlayPauseClick) {
                    Icon(
                        painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
                IconButton(onClick = onNextClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.next_arrow_forward_svgrepo_com),
                        contentDescription = "Next",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }