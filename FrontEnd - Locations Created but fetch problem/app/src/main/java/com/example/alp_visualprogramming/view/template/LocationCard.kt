package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // Import layout components
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.models.Location
import com.example.alp_visualprogramming.R

@Composable
fun LocationCard(location: Location, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.76.dp)
            .height(112.05.dp)
            .clickable(onClick = onClick), // Add clickable modifier here
        shape = RoundedCornerShape(21.145.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF5FEEDB)) // Background color 5FEEDB
        ) {
            // Fixed background image
            Image(
                painter = painterResource(id = R.drawable.bglocation), // Use placeholder image
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(71.88.dp)
                    .align(Alignment.BottomCenter) // Align the image at the bottom center
            )

            // Overlay text
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(8.dp)) // Small spacing at the top

                // Location Name
                location.name?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 21.14.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),
                        )
                    )
                }

                // Location Address directly below the name
                Spacer(modifier = Modifier.height(4.dp)) // Small spacing between the texts

                Text(
                    text = location.address ?: "No Address Provided", // Use fallback text
                    style = TextStyle(
                        fontSize = 10.57.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF440215),
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    LocationCard(
        location = Location(
            id = 1,
            name = "Pakuwon Mall",
            address = "Jl. Mayjend. Jonosewojo, Surabaya",
            categories = listOf("Mall", "Shopping"),
            website = "https://pakuwonmall.com",
            phone = "+62 811 331 8811",
            openingHours = "10:00 AM - 10:00 PM",
            placeId = "example_place_id"
        ),
        onClick = {}
    )
}
