package aminor.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
/*import aminor.model.apidataclasses.ApiData
import aminor.model.apicallerclasses.FetchData*/
import com.example.aminormusic.model.apicallerclasses.FetchData
import com.example.aminormusic.model.apidataclasses.ApiData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val fetchData = FetchData()

    private val _currentPlayingIndex = MutableStateFlow(0)
    val currentPlayingIndex: StateFlow<Int> = _currentPlayingIndex

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _results = MutableStateFlow<ApiData?>(null)
    val results: StateFlow<ApiData?> = _results

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _noResultsFound = MutableStateFlow(false)
    val noResultsFound: StateFlow<Boolean> = _noResultsFound

    private val _apiError = MutableStateFlow(false)
    val apiError: StateFlow<Boolean> = _apiError

    private var playlist: List<String> = listOf()
    private var mediaPlayer: MediaPlayer? = null

    // Update query when it changes
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    // Perform the search and update results
    fun search() {
        viewModelScope.launch {
            _isLoading.value = true
            fetchData.fetchData(query.value) { response ->
                _isLoading.value = false
                if (response != null) {
                    _results.value = response
                    if (response.data.isNullOrEmpty()) {
                        // Display "No result found" message
                        _noResultsFound.value = true
                    } else {
                        _noResultsFound.value = false
                        // Automatically set the playlist from the search results
                        val previews = response.data.map { it.preview }
                        setPlaylist(previews)
                    }
                } else {
                    // Handle API error
                    _apiError.value = true
                }
            }
        }
    }

    // Set the playlist and start playing the first song
    private fun setPlaylist(newPlaylist: List<String>) {
        playlist = newPlaylist
        if (playlist.isNotEmpty()) {
            _currentPlayingIndex.value = 0
            playMusic(playlist[_currentPlayingIndex.value])
        }
    }

    // Play the music from the preview URL
    fun playMusic(previewUrl: String) {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(previewUrl)
            prepare()
            start()
        }
        _isPlaying.value = true
    }

    // Pause the currently playing music
    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
            }
        }
    }

    // Resume the currently paused music
    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                _isPlaying.value = true
            }
        }
    }

    // Play the previous song in the playlist
    fun playPreviousSong() {
        if (playlist.isNotEmpty()) {
            _currentPlayingIndex.value = (_currentPlayingIndex.value - 1 + playlist.size) % playlist.size
            playMusic(playlist[_currentPlayingIndex.value])
        }
    }

    // Play the next song in the playlist
    fun playNextSong() {
        if (playlist.isNotEmpty()) {
            _currentPlayingIndex.value = (_currentPlayingIndex.value + 1) % playlist.size
            playMusic(playlist[_currentPlayingIndex.value])
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}