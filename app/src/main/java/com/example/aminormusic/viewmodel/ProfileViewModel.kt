package com.example.aminormusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    val name: String
        get() = email.substringBefore("@") // Extracts the part before the '@'

    val country: String = "India"
    val phoneNumber: String = "9999999999"
    var isLoggedIn by mutableStateOf(false) // Tracks if the user is logged in

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Track the current Firebase user
    var currentUser by mutableStateOf<FirebaseUser?>(null)
        private set

    // Track login errors
    var loginError by mutableStateOf<String?>(null)
        private set

    init {
        // Initialize the current user
        currentUser = auth.currentUser
        isLoggedIn = currentUser != null
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        loginError = null // Clear error when user changes email
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        loginError = null // Clear error when user changes password
    }

    fun onLoginClick(onSuccess: () -> Unit, onFailure: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                currentUser = auth.currentUser
                isLoggedIn = currentUser != null
                if (isLoggedIn) {
                    loginError = null
                    withContext(Dispatchers.Main) {
                        onSuccess() // Call the success callback to navigate
                    }
                } else {
                    loginError = "Login failed"
                    withContext(Dispatchers.Main) {
                        onFailure() // Call the failure callback
                    }
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                loginError = "Invalid email or password"
                withContext(Dispatchers.Main) {
                    onFailure() // Call the failure callback
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                loginError = "User does not exist"
                withContext(Dispatchers.Main) {
                    onFailure() // Call the failure callback
                }
            } catch (e: Exception) {
                loginError = "Login failed: ${e.localizedMessage}"
                withContext(Dispatchers.Main) {
                    onFailure() // Call the failure callback
                }
            }
        }
    }

    fun onSignUpClick() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                currentUser = auth.currentUser
                isLoggedIn = currentUser != null
            } catch (e: Exception) {
                // Handle sign-up failure
                loginError = "Sign-up failed: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }

    fun onLogoutClick() {
        auth.signOut()
        currentUser = null
        isLoggedIn = false
    }
}
