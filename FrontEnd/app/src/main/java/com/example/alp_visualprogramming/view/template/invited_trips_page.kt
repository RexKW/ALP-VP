package com.example.alp_vp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R


// 🔹 Data Class untuk Trip
data class Trip(
    val title: String,
    val fromDate: String,
    val toDate: String,
    val travelers: Int,
    val label: String
)

// 🔹 Dummy Data untuk 5 Trip
val sampleTrips = listOf(
    Trip("Bali Adventure", "13 Dec 2024", "1 Jan 2025", 5, "Pete's Trip Plan"),
    Trip("Jogja Culinary", "20 Dec 2024", "5 Jan 2025", 4, "Pete's Trip Plan"),
    Trip("Bandung Escape", "10 Jan 2025", "20 Jan 2025", 3, "Pete's Trip Plan"),
    Trip("Lombok Retreat", "5 Feb 2025", "15 Feb 2025", 6, "Pete's Trip Plan"),
    Trip("Surabaya Explorer", "1 Mar 2025", "10 Mar 2025", 2, "Pete's Trip Plan")
)

// 🔹 Halaman Utama Invited Trips
@Composable
fun invited_trips_page(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7))
    ) {
        // 🔹 Footer Image di Bagian Bawah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backdrop),
                contentDescription = "Bottom Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 🔹 Konten Utama (Navigasi dan Card)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            // 🔹 Bagian Navigasi di Atas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Trips",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3E122B),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Invited Trips",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFEE417D),
                    textAlign = TextAlign.Center,
                )
            }

            // 🔹 Daftar Scrollable Trip Cards
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleTrips) { trip ->
                    TripCard(trip)
                }
            }
        }
    }
}

// 🔹 Komponen Trip Card
@Composable
fun TripCard(trip: Trip) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(206.dp)
            .background(
                color = Color(0xFF5FEEDB),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Judul Utama
                Text(
                    text = trip.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 36.sp
                )

                // Tanggal
                Text(
                    text = "From: ${trip.fromDate}",
                    fontSize = 12.sp,
                    color = Color(0xFF004D4B)
                )
                Text(
                    text = "To: ${trip.toDate}",
                    fontSize = 12.sp,
                    color = Color(0xFF004D4B)
                )

                // Jumlah Travelers
                Text(
                    text = "${trip.travelers}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEE417D)
                )
                Text(
                    text = "Travelers",
                    fontSize = 14.sp,
                    color = Color(0xFF004D4B)
                )
            }

            // Ilustrasi Gedung
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(133.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.building_5),
                    contentDescription = "City Illustration",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(width = 140.dp, height = 140.dp)
                        .clip(
                            RoundedCornerShape(bottomEnd = 16.dp)
                        )
                )
            }
        }

        // Label di bagian bawah kanan
        Text(
            text = trip.label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4DF1E7),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp)
        )
    }
}

// 🔹 Preview
@Preview(showBackground = true)
@Composable
fun invited_trips_pagePreview() {

        invited_trips_page("Android")

}
