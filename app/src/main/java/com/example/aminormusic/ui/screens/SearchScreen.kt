package com.example.aminormusic.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aminormusic.ui.customlayouts.CustomCardLayout
import com.example.aminormusic.viewmodel.SearchViewModel

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    val query by searchViewModel.query.collectAsState()  // Observe the query state
    val results by searchViewModel.results.collectAsState()  // Observe the results state
    val isLoading by searchViewModel.isLoading.collectAsState()


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { searchViewModel.onQueryChanged(it) },  // Update the query state on text change
            label = { Text("Enter query") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { searchViewModel.search() },  // Trigger the search when the button is clicked
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            Text("Loading...", fontSize = 20.sp, color = Color.Gray)
        } else {
            // Display results if available
            results?.let {
                CustomCardLayout(it, onPlayButtonClick = { previewUrl ->
                    searchViewModel.playMusic(previewUrl)
                })
            } ?: Text("No results found.")
        }

    }
}


