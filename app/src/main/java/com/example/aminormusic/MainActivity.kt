package com.example.aminormusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.aminormusic.ui.theme.AminorMusicTheme
import com.example.aminormusic.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AminorMusicTheme {
                // A surface container using the 'background' color from the theme
                MainViewModel()
            }
        }
    }
}
