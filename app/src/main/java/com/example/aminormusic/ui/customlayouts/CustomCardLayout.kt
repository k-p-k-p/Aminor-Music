package com.example.aminormusic.ui.customlayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.aminormusic.model.apidataclasses.ApiData

@Composable
fun CustomCardLayout(data: ApiData, onPlayButtonClick: (String) -> Unit) {
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
                    Text("Title: ${item.title}", color = Color.Black , fontWeight = FontWeight.Bold , fontSize = 20.sp)
//                    Text("Album: ${item.album.title}" , fontWeight = FontWeight.Bold)
                    Text("Artist: ${item.artist.name}" , fontStyle = FontStyle.Italic , fontWeight = FontWeight.Bold , fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onPlayButtonClick(item.preview) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.hsv(0F, 0F, 0.9882F),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Play", fontSize = 20.sp, color = Color.Cyan)
                    }
                }
            }
        }
    }
}
