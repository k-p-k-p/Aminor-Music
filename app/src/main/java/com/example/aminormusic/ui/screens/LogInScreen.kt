package com.example.aminormusic.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aminormusic.R
import com.example.aminormusic.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LogInScreen(navController: NavHostController, viewModel: ProfileViewModel = viewModel()) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121111))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.aminor_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(275.dp)
                    .height(215.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Login to your account" text
            Text(
                text = "Login to your account",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(355.dp)
                    .height(36.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email text field
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = null,
                        Modifier.size(26.dp),
                        tint = Color(0xFFAAAAAA)
                    )
                },
                modifier = Modifier
                    .width(340.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password text field
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_padlock),
                        contentDescription = null,
                        Modifier.size(28.dp),
                        tint = Color(0xFFAAAAAA)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_hide else R.drawable.ic_show),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            Modifier.size(26.dp),
                            tint = Color(0xFFAAAAAA)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .width(340.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Remember me" text with checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(18.dp)
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Remember me",
                    fontSize = 14.sp,
                    color = Color(0xFFAAAAAA)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login button
            Button(
                onClick = {
                    viewModel.onLoginClick(
                        onSuccess = { navController.navigate("home") },
                        onFailure = { /* Handle failure */ }
                    )
                },
                modifier = Modifier
                    .width(340.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06A0B5))
            ) {
                Text(
                    text = "Login ",
                    fontSize = 20.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            // Display login error if any
            Spacer(modifier = Modifier.height(16.dp))
            viewModel.loginError?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Divider with "or continue with" text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(353.dp)
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "Or continue with",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color(0xFFFFFFFF)
                )
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Google, Facebook, Apple buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                IconButton(onClick = { /* Handle Google sign-in */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                IconButton(onClick = { /* Handle Facebook sign-in */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                IconButton(onClick = { /* Handle Apple sign-in */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color(0xFFFFFFFF))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "Don't have an account? Sign Up" text with Sign Up as a button
            Row {
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp,
                        color = Color(0xFFFFFFFF)
                    )
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        color = Color(0xFF06A0B5)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogInScreen() {
    LogInScreen(rememberNavController())
}
