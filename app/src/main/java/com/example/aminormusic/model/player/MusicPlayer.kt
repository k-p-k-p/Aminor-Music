package com.example.aminormusic.model.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MusicPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var playlist: List<String> = emptyList()
    private var currentIndex: Int = -1

    fun setPlaylist(songs: List<String>, startIndex: Int = 0) {
        playlist = songs
        currentIndex = startIndex
        // Initialize player if necessary
        if (playlist.isNotEmpty()) {
            playMusic(playlist[currentIndex])
        }
    }

    fun playMusic(previewUrl: String) {
        // Release any existing media player
        mediaPlayer?.release()

        // Create a new media player
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, Uri.parse(previewUrl))
            setOnPreparedListener { it.start() }
            prepareAsync()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        mediaPlayer?.start()
    }

    fun playPreviousSong() {
        if (playlist.isNotEmpty() && currentIndex > 0) {
            currentIndex--
            playMusic(playlist[currentIndex])
        }
    }

    fun playNextSong() {
        if (playlist.isNotEmpty() && currentIndex < playlist.size - 1) {
            currentIndex++
            playMusic(playlist[currentIndex])
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
