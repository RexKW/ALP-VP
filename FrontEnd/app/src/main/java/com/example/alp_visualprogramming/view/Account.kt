package com.example.alp_vp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.ui.theme.ALP_VisualProgrammingTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AccountScreen(

) {
    // State untuk username dari ViewModel
    val tripsCount = 5
    val followedTripsCount = 3

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7))
    ) {
        // 🔹 Background Atas
        Image(
            painter = painterResource(id = R.drawable.group_21),
            contentDescription = "Top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
        )

        // 🔹 Profil Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopCenter)
                .offset(y = 240.dp)
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

        // 🔹 Username dan Info Akun
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 375.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rex",
                color = Color(0xFFEE417D),
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEE417D),

                    )
            )
            Text(
                text = "Account Created: 26 December 2024",
                color = Color(0xFFEE4482),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 🔹 Statistik (Your Trips dan Followed Trips)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 500.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(378.dp)
                    .height(200.dp)
                    .background(color = Color(0xFF94284D).copy(alpha = 1f), shape = RoundedCornerShape(size = 21.14.dp))
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Your Trips",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )
                        )
                        Text(
                            text = tripsCount.toString(), // Dinamis berdasarkan data
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFEE417D),

                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Followed Trips",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )
                        )
                        Text(
                            text = followedTripsCount.toString(), // Dinamis berdasarkan data

                                    style = TextStyle(
                                    fontSize = 48.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),

                            textAlign = TextAlign.Center,
                        )
                        )
                    }
                }
            }

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
fun AccountScreenPreview() {
    ALP_VisualProgrammingTheme {
        AccountScreen()
    }
}