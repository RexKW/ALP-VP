package com.example.alp_vp.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp.R
import com.example.alp_vp.ui.theme.ALP_VPTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

@Composable
fun account(name: String, modifier: Modifier = Modifier, context: Context) {

    // State untuk password dan visibility
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    // Mengambil tanggal pembuatan akun dari SharedPreferences
//    val accountCreationDate = getAccountCreationDate(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7))
    ) {
        // 🔹 Background Atas (group_21)
        Image(
            painter = painterResource(id = R.drawable.group_21),
            contentDescription = "Top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
        )

        // 🔹 Profil Picture (Antara Background Atas dan Layar)
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopCenter)
                .offset(y = 240.dp) // Mengatur agar berada di antara kedua elemen
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_circle),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
        }

        // 🔹 Username dan Edit Icon
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 375.dp), // Di bawah profil picture
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    color = Color(0xFFEE417D),
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Username",
                    tint = Color(0xFFEE417D),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { /* Action for editing username */ }
                )
            }

            // 🔹 OutlinedTextField untuk Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Icon untuk toggle visibility (Mata)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                ),
                                contentDescription = "Toggle Password"
                            )
                        }
                        // Icon untuk Edit Password
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Password",
                            tint = Color(0xFFEE417D),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { /* Action for editing password */ }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            // 🔹 Tanggal Pembuatan Akun
            Text(
                // text = "Account Created: $accountCreationDate",
                text = "Account Created: 26 December 2024",
                color = Color(0xFFEE4482),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 🔹 Box untuk Your Trips dan Followed Trips
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 550.dp) // Padding agar box dan footer berada di bawah informasi
        ) {
            // Box untuk Your Trips dan Followed Trips
            Box(
                modifier = Modifier
                    .width(378.dp)
                    .height(120.dp)
                    .background(color = Color(0xFF94284D).copy(alpha = 1f), shape = RoundedCornerShape(size = 21.14.dp))
                    .padding(16.dp)  // Padding dalam box
                    .align(Alignment.TopCenter)  // Menempatkan Box di tengah
            ) {

            Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Your Trips Section
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Your Trips",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "5", // Example, you can replace this with actual data
                            color = Color.White,
                            fontSize = 32.sp
                        )
                    }

                    // Followed Trips Section
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Followed Trips",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "3", // Example, you can replace this with actual data
                            color = Color.White,
                            fontSize = 32.sp
                        )
                    }
                }
            }

            // 🔹 Footer Background (Backdrop) di bagian paling bawah
            Image(
                painter = painterResource(id = R.drawable.backdrop),
                contentDescription = "Bottom Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun accountPreview() {
    ALP_VPTheme {
        val context = LocalContext.current
        account("Android", context = context)
    }
}
