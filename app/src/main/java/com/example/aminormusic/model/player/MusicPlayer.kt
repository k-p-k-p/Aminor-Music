package com.example.aminormusic.model.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MusicPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

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

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
