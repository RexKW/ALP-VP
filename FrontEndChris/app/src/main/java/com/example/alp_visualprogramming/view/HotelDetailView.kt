package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.models.Location
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun HotelDetailView(location: Location, navController: NavController) {
    val price = remember { mutableStateOf("") } // Holds the price entered by the user
    val occupants = remember { mutableStateOf(2) } // Default to 2 occupants
    val rooms = (occupants.value + 1) / 2 // Calculate number of rooms based on occupants
    val totalCost = remember { mutableStateOf(0) }

    // Calculate the total cost dynamically whenever price or occupants change
    LaunchedEffect(price.value, occupants.value) {
        val pricePerRoom = price.value.toIntOrNull() ?: 0
        totalCost.value = rooms * pricePerRoom
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7)) // Background color FBF7E7
    ) {
        // Backdrop image at the bottom
        Image(
            painter = painterResource(R.drawable.backdrop),
            contentDescription = "Backdrop image",
            modifier = Modifier
                .fillMaxWidth()
                .height(556.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Make the content scrollable
            verticalArrangement = Arrangement.SpaceBetween // Push content to top and bottom
        ) {
            // Top Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Top Image with Back Arrow
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(237.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hotelplaceholderalp),
                        contentDescription = "Placeholder image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Image(
                        painter = painterResource(id = R.drawable.locationdetailfilteralp),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Filter overlay"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.arrowbackalp),
                        contentDescription = "Back button",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(1.dp)
                            .offset(x = 14.dp, y = 10.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }

                // Main Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = location.name,
                        fontSize = 40.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D),
                        style = TextStyle(lineHeight = 50.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = location.address ?: "No address available",
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF440215),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    location.categories.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            text = "Categories: ${it.joinToString(", ")}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    location.openingHours?.let {
                        Text(
                            text = "Opening Hours: $it",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    location.website?.let {
                        Text(
                            text = "Website: $it",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    location.phone?.let {
                        Text(
                            text = "Phone: $it",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                    }
                }
            }

            // Bottom Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price per room:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    BasicTextField(
                        value = price.value,
                        onValueChange = { price.value = it },
                        modifier = Modifier
                            .background(Color(0xFFEAE5D2), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .width(100.dp),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF5FEEDB), shape = RoundedCornerShape(size = 20.dp))
                        .padding(16.dp) // Padding inside the box
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = occupants.value.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Occupants",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Minus",
                            modifier = Modifier
                                .width(31.dp)
                                .height(5.dp)
                                .clickable {
                                    if (occupants.value > 1) {
                                        occupants.value -= 1
                                    }
                                }
                        )

                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = "Plus",
                            modifier = Modifier
                                .padding(0.dp)
                                .width(31.dp)
                                .height(31.dp)
                                .clickable {
                                    occupants.value += 1
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cost:",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEE417D)
                    )
                    Text(
                        text = "${totalCost.value} (total)",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEE417D)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .background(Color(0xFFEE417D), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp)
                        .clickable { /* TODO: Add functionality */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Choose",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}