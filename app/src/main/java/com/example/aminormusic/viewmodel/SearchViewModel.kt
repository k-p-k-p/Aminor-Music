import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aminormusic.model.apidataclasses.ApiData
import com.example.aminormusic.model.apicallerclasses.FetchData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.media.MediaPlayer

class SearchViewModel : ViewModel() {
    private val fetchData = FetchData()

    private val _currentPlaying = MutableStateFlow<String?>(null)
    val currentPlaying: StateFlow<String?> = _currentPlaying

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _results = MutableStateFlow<ApiData?>(null)
    val results: StateFlow<ApiData?> = _results

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var playlist: List<String> = listOf()
    private var currentIndex: Int = -1

    private var mediaPlayer: MediaPlayer? = null

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        viewModelScope.launch {
            _isLoading.value = true
            fetchData.fetchData(query.value) { response ->
                _results.value = response
                _isLoading.value = false
            }
        }
    }

    fun setPlaylist(newPlaylist: List<String>) {
        playlist = newPlaylist
        currentIndex = if (playlist.isNotEmpty()) 0 else -1
        if (currentIndex != -1) {
            playMusic(playlist[currentIndex])
        }
    }

    fun playMusic(previewUrl: String) {
        if (_currentPlaying.value != previewUrl) {
            _currentPlaying.value = previewUrl
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(previewUrl)
                prepare()
                start()
            }
            _isPlaying.value = true
        } else {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    _isPlaying.value = true
                }
            }
        }
    }

    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
            }
        }
    }

    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                _isPlaying.value = true
            }
        }
    }

    fun playPreviousSong() {
        if (playlist.isNotEmpty() && currentIndex != -1) {
            currentIndex = (currentIndex - 1 + playlist.size) % playlist.size
            playMusic(playlist[currentIndex])
        }
    }

    fun playNextSong() {
        if (playlist.isNotEmpty() && currentIndex != -1) {
            currentIndex = (currentIndex + 1) % playlist.size
            playMusic(playlist[currentIndex])
        }
    }
}
