package com.example.aminormusic.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aminormusic.R


@Composable
    fun WelcomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Fills the entire available space
            .background(Color(0xFF121111)) // Replace with your desired color
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
                    .width(325.dp)
                    .height(254.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Let's get you in" text
            Text(
                text = "Let's get you in",
                fontSize = 28.sp, // Adjusted to fit the required width and height
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(334.dp)
                    .height(50.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Google button
            OutlinedButton(
                onClick = { /* Handle Google sign-in */ },
                modifier = Modifier
                    .width(330.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Continue with Google",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Facebook button
            OutlinedButton(
                onClick = { /* Handle Facebook sign-in */ },
                modifier = Modifier
                    .width(330.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Continue with Facebook",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Apple button
            OutlinedButton(
                onClick = { /* Handle Apple sign-in */ },
                modifier = Modifier
                    .width(330.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Continue with Apple",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Divider with "or" text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(353.dp)
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "or",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color(0xFFFFFFFF)
                )
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login with a password button
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .width(340.dp)
                    .height(59.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06A0B5)) // Adjust color as needed
            ) {
                Text(
                    text = "Login with a password",
                    fontSize = 16.sp,
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        color = Color(0xFF00BCD4) // Adjust color as needed
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(rememberNavController())
}

