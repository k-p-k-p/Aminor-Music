import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aminormusic.R
/*import com.example.aminormusic.ui.customlayouts.CustomCardLayout
import com.example.aminormusic.viewmodel.SearchViewModel*/

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    val query by searchViewModel.query.collectAsState()
    val results by searchViewModel.results.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val currentPlaying by searchViewModel.currentPlaying.collectAsState()
    val isPlaying by searchViewModel.isPlaying.collectAsState()  // Add this line

    Column(modifier = Modifier.padding(16.dp)) {
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
            results?.let {
                if (it.data.isNotEmpty()) {
                    CustomCardLayout(
                        data = it,
                        currentPlaying = currentPlaying,
                        isPlaying = isPlaying,  // Pass isPlaying to CustomCardLayout
                        onPlayButtonClick = { previewUrl ->
                            searchViewModel.playMusic(previewUrl)
                        },
                        onPauseButtonClick = {
                            searchViewModel.pauseMusic()
                        },
                        onResumeButtonClick = {
                            searchViewModel.resumeMusic()
                        },
                        onPreviousButtonClick = {
                            searchViewModel.playPreviousSong()
                        },
                        onNextButtonClick = {
                            searchViewModel.playNextSong()
                        }
                    )
                } else {
                    Text("No results found.")
                }
            } ?: Text("No results found.")
        }
    }
}
