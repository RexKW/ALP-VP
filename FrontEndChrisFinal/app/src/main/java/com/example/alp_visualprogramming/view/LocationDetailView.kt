package com.example.alp_visualprogramming.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.models.Location
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel

@Composable
fun LocationDetailView(location: Location, navController: NavController, activityFormViewModel: ActivityFormViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7)) // Background color
    ) {
        // Backdrop image at the bottom
        Image(
            painter = painterResource(id = R.drawable.backdrop),
            contentDescription = "Backdrop image",
            modifier = Modifier
                .fillMaxWidth()
                .height(556.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        // Content on top of the backdrop
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top Image with Back Arrow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp) // Same height as the previous page's top image
            ) {
                // Placeholder Image
                Image(
                    painter = painterResource(id = R.drawable.locationplaceholderalp),
                    contentDescription = "Placeholder image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Filter Image (Over the background but below the arrow)
                Image(
                    painter = painterResource(id = R.drawable.locationdetailfilteralp),
                    contentDescription = "Filter overlay",
                    contentScale = ContentScale.FillBounds, // Ensure it spans the entire area
                    modifier = Modifier.fillMaxSize() // Covers the whole box
                )

                // Back Arrow
                Image(
                    painter = painterResource(id = R.drawable.arrowbackalp),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(1.dp)
                        .offset(x = 14.dp, y = 10.dp) // Move the arrow slightly
                        .clickable {
                            navController.popBackStack() // Navigate back to the previous screen
                        }
                )
            }

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Location Name
                Text(
                    text = location.name,
                    fontSize = 40.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEE417D),
                    style = TextStyle(lineHeight = 50.sp) // Increase line spacing
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Location Address
                Text(
                    text = location.address ?: "No address available",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF440215),
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Categories
                if (location.categories.isNotEmpty()) {
                    Text(
                        text = "Categories: ${location.categories.joinToString(", ")}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E122B),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Opening Hours
                location.openingHours?.let { openingHours ->
                    Text(
                        text = "Opening Hours: $openingHours",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E122B),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Website
                location.website?.let { website ->
                    Text(
                        text = "Website: $website",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E122B),
                        modifier = Modifier.clickable {
                            navController.context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(website))
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Phone
                location.phone?.let { phone ->
                    Text(
                        text = "Phone: $phone",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E122B),
                        modifier = Modifier.clickable {
                            navController.context.startActivity(
                                Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(34.dp))
                // Choose Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .background(Color(0xFFEE417D), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp)
                        .clickable {
                            // Update the selected location in ViewModel
                            activityFormViewModel.updateSelectedLocation(location)
                            // Navigate back to ActivityFormView
                            navController.popBackStack()
                        },
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
