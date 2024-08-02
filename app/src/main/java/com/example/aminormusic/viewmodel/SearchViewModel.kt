package com.example.aminormusic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aminormusic.model.apicallerclasses.FetchData
import com.example.aminormusic.model.apidataclasses.ApiData
import com.example.aminormusic.model.player.MusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository = FetchData()  // Create an instance of the Repository

    private val _query = MutableStateFlow("")  // Mutable state for the query
    val query: StateFlow<String> get() = _query  // Publicly exposed state for observing

    private val _results = MutableStateFlow<ApiData?>(null)  // Mutable state for the results
    val results: StateFlow<ApiData?> get() = _results  // Publicly exposed state for observing

    private val _isLoading = MutableStateFlow(false) // New state for loading
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val musicPlayer = MusicPlayer(application)

    // Function to update the query state
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    // Function to trigger the search
    fun search() {
        viewModelScope.launch {
            _isLoading.value = true // Set loading to true
            repository.fetchData(_query.value) { data ->
                _results.value = data
                _isLoading.value = false // Set loading to false
            }
        }
    }

    fun playMusic(previewUrl: String) {
        musicPlayer.playMusic(previewUrl)
    }

    override fun onCleared() {
        super.onCleared()
        // Release media player resources when the ViewModel is cleared
        musicPlayer.release()
    }
}