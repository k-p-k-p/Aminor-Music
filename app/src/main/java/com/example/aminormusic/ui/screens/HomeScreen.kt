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
import androidx.compose.material.Surface
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.aminormusic.model.interfaces.ApiInterface
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

@Composable
fun HomeScreen() {
    // Replace with your Home screen content
    Surface(modifier = Modifier.fillMaxSize()) {
        MyApp()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp()
{
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color.White,
            onPrimary = Color.Black,
            surface = Color.White,
            onSurface = Color.Black
        )
    )
    {
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
                    backgroundColor =  MaterialTheme.colorScheme.primary,
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
    var musicCards by remember { mutableStateOf<List<MusicCardData>>(emptyList()) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                // Make the API call with a search query, e.g., "eminem"
                val response = RetrofitInstance.api.getData("eminem").execute()
                if (response.isSuccessful) {
                    // Map the response data to MusicCardData
                    val apiData = response.body()
                    musicCards = apiData?.data?.map { MusicCardData(it.album.cover) } ?: emptyList()
                } else {
                    // Handle unsuccessful response
                    musicCards = emptyList()
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                musicCards = emptyList()
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
        Text(text = "Continue Listening")
        CardRow(imageUrls = musicCards.map { it.imageUrl })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your Top Mixes")
        CardRow(imageUrls = musicCards.map { it.imageUrl })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Based on your recent listening")
        CardRow(imageUrls = musicCards.map { it.imageUrl })
    }
}


@Composable
fun CardRow(imageUrls: List<String>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imageUrls.size) { index ->
            MusicCard(imageUrl = imageUrls[index])
        }
    }
}

@Composable
fun MusicCard(imageUrl: String) {
    Card(modifier = Modifier
        .width(120.dp)
        .height(150.dp), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
        containerColor = Color.Black,
        contentColor = Color.White
    )) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build()
            ),
            contentDescription = "Music Album Art",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
/*// retrofit url extract
interface ApiService {
    @Headers("x-rapidapi-key: 3d6178deb1mshf75399de754f8d6p1767fejsn859830a9af27" , "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search") // Replace with your API endpoint
    suspend fun fetchMusicCards(): List<MusicCardData>
}*/

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}

//data class
data class MusicCardData(
    val imageUrl: String
)
