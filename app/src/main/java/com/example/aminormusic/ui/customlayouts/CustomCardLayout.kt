import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.aminormusic.R
import com.example.aminormusic.model.apidataclasses.ApiData

@Composable
fun CustomCardLayout(
    data: ApiData,
    currentPlaying: String?,
    isPlaying: Boolean,
    onPlayButtonClick: (String) -> Unit,
    onPauseButtonClick: () -> Unit,
    onResumeButtonClick: () -> Unit,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    LazyColumn {
        item {
            Text("Total Results: ${data.total}", modifier = Modifier.padding(16.dp))
        }
        items(data.data) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.hsv(34.81F, 0.3176F, 1.0F)),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = item.album.cover_big),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Title: ${item.title}", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Artist: ${item.artist.name}", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onPreviousButtonClick() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_previous),
                                contentDescription = "Previous",
                                tint = Color.White
                            )
                        }
                        AnimatedVisibility(
                            visible = item.preview == currentPlaying && isPlaying,
                            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
                        ) {
                            IconButton(onClick = { onPauseButtonClick() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_pause),
                                    contentDescription = "Pause",
                                    tint = Color.White
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = item.preview == currentPlaying && !isPlaying,
                            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
                        ) {
                            IconButton(onClick = { onResumeButtonClick() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = item.preview != currentPlaying,
                            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
                        ) {
                            IconButton(onClick = { onPlayButtonClick(item.preview) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(onClick = { onNextButtonClick() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.next_arrow_forward_svgrepo_com),
                                contentDescription = "Next",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
