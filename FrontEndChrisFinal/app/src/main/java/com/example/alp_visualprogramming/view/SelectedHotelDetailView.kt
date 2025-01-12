package com.example.alp_visualprogramming.view

import android.util.Log
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
import com.example.alp_visualprogramming.models.AccommodationRequest
import com.example.alp_visualprogramming.viewModel.JourneyViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.models.AccommodationResponse
import kotlinx.coroutines.launch


@Composable
fun SelectedHotelDetailView(
    navController: NavController,
    accommodation: AccommodationResponse, // Pass the fetched accommodation details
    cityName: String // Pass cityName to navigate to the HotelListView
) {
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
                        text = accommodation.name,
                        fontSize = 40.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D),
                        style = TextStyle(lineHeight = 50.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = accommodation.address ?: "No address available",
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF440215),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    accommodation.categories.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            text = "Categories: ${it.joinToString(", ")}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    accommodation.opening_hours?.let {
                        Text(
                            text = "Opening Hours: $it",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    accommodation.website?.let {
                        Text(
                            text = "Website: $it",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    accommodation.phone?.let {
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
                        text = "Total Cost:",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEE417D)
                    )
                    Text(
                        text = "${accommodation.cost}",
                        fontSize = 35.sp,
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
                        .clickable {
                            navController.navigate("HotelList/$cityName/${accommodation.id}")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Change",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

//@Composable
//fun SelectedHotelDetailView(
//    navController: NavController,
//    accommodation: Accommodation, // Pass the Accommodation object from the backend
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFFBF7E7)) // Background color FBF7E7
//    ) {
//        // Backdrop image at the bottom
//        Image(
//            painter = painterResource(R.drawable.backdrop),
//            contentDescription = "Backdrop image",
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(556.dp)
//                .align(Alignment.BottomCenter),
//            contentScale = ContentScale.Crop
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            // Top Content
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                // Top Image with Back Arrow
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(237.dp)
//                ) {
//                    Image(
//                        painter = rememberImagePainter(data = accommodation.location_image), // Dynamically load image
//                        contentDescription = "Hotel image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize()
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.locationdetailfilteralp),
//                        contentScale = ContentScale.FillBounds,
//                        modifier = Modifier.fillMaxSize(),
//                        contentDescription = "Filter overlay"
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.arrowbackalp),
//                        contentDescription = "Back button",
//                        modifier = Modifier
//                            .size(35.dp)
//                            .padding(1.dp)
//                            .offset(x = 14.dp, y = 10.dp)
//                            .clickable {
//                                navController.popBackStack()
//                            }
//                    )
//                }
//
//                // Main Content
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = accommodation.name,
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFEE417D),
//                        style = TextStyle(lineHeight = 50.sp)
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = accommodation.address,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight(500),
//                        color = Color(0xFF440215),
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    if (accommodation.categories.isNotEmpty()) {
//                        Text(
//                            text = "Categories: ${accommodation.categories.joinToString(", ")}",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFF3E122B),
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//
//                    accommodation.opening_hours?.let {
//                        Text(
//                            text = "Opening Hours: $it",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFF3E122B),
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//
//                    accommodation.website?.let {
//                        Text(
//                            text = "Website: $it",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFF3E122B),
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//
//                    accommodation.phone?.let {
//                        Text(
//                            text = "Phone: $it",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight(400),
//                            color = Color(0xFF3E122B),
//                        )
//                    }
//                }
//            }
//
//            // Bottom Section
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Total Cost:",
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFFEE417D)
//                    )
//                    Text(
//                        text = "${accommodation.cost} (total)",
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFFEE417D)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(62.dp)
//                        .background(Color(0xFFEE417D), RoundedCornerShape(20.dp))
//                        .padding(horizontal = 16.dp)
//                        .clickable {
//                            navController.navigate("HotelList/${accommodation.address}/${accommodation.id}")
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Change",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(100.dp))
//            }
//        }
//    }
//}