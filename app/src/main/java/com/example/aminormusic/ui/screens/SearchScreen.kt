import aminor.viewmodel.SearchViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.aminormusic.R

class MainActivity : androidx.activity.ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(searchViewModel)
        }
    }
}

@Composable
fun SearchScreen(searchViewModel: SearchViewModel) {
    val query by searchViewModel.query.collectAsState()
    val results by searchViewModel.results.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val currentPlayingIndex by searchViewModel.currentPlayingIndex.collectAsState()
    val isPlaying by searchViewModel.isPlaying.collectAsState()
    val noResultsFound by searchViewModel.noResultsFound.collectAsState()
    val apiError by searchViewModel.apiError.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = query,
            onValueChange = { searchViewModel.onQueryChanged(it) },
            label = { Text("Enter query") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { searchViewModel.search() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))


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
                        Text("No results found.")
                    }
                } ?: Text("No results found.")
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