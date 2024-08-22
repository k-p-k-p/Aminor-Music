package com.example.aminormusic.viewmodel
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class ProfileViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    val name: String
        get() = email.substringBefore("@") // Extracts the part before the '@'

    val country: String = "India"
    val phoneNumber: String = "9999999999"
    var isLoggedIn by mutableStateOf(false) // Tracks if the user is logged in

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onLoginClick() {
        //handle firebase login logic
        isLoggedIn = true
    }
}