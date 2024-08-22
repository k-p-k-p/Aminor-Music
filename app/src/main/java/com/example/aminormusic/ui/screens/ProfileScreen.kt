package com.example.aminormusic.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aminormusic.R
import com.example.aminormusic.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen(navController: NavHostController,viewModel: ProfileViewModel = viewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0e3135), // Start color
                            Color(0xFF0e3135), // Transition color (same as start to cover 70%)
                            Color(0xFF000000)  // End color
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY),
                        tileMode = TileMode.Clamp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Padding inside the Box
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.navigate("home") },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = null,
                            Modifier.size(30.dp),
                            tint = Color(0xFFFFFFFF)
                        )

                    }
                }
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.aminor_logo),
                            contentDescription = "Jetpack compose image",
                            modifier = Modifier.size(150.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = " Profile",
                            fontSize = 30.sp, // Adjusted to fit the required width and height
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(355.dp)
                                .height(36.dp),
                            textAlign = TextAlign.Start,
                            color = Color(0xFFFFFFFF)
                        )
                        EditableField(label = "Name", value = viewModel.name)
                        EditableField(label = "Email ID", value = viewModel.email)
                        EditableField(label = "Country", value = viewModel.country)
                        EditableField(label = "Phone Number", value = viewModel.phoneNumber)
                    }
                }
            }
        }
    }

@Composable
fun EditableField(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {}, // Handle the onValueChange for edit functionality
            label = { Text(label, color = Color(0xFFFFFFFF)) },
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit $label",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(24.dp),
            tint = Color(0xFFFFFFFF)// Adjust size of the icon
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AfterLoginPreview() {
    ProfileScreen(rememberNavController())
}
