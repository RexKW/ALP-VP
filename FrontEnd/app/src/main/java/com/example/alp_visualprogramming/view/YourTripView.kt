package com.example.alp_visualprogramming.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.TripsViewModel

@Composable
fun YourTripView(
    modifier: Modifier = Modifier,
    navController: NavController,
    token: String,
    tripsViewModel: TripsViewModel,
    context: Context
) {
    val dataStatus = tripsViewModel.dataStatus

    // Memuat data ketika token berubah
    LaunchedEffect(token) {
        if (token != "Unknown") {
            tripsViewModel.getAllItineraries(token)
        }
    }

    // Menangani error ketika terjadi kesalahan data
    LaunchedEffect(dataStatus) {
        if (dataStatus is TripDataStatusUIState.Failed) {
            Toast.makeText(context, "Error: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            tripsViewModel.clearDataErrorMessage()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header dengan judul
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Your Trips",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEE417D),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Invited Trips",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3E122B),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.Start)
            )
        }

        // Handling UI state berdasarkan dataStatus
        when (dataStatus) {
            is TripDataStatusUIState.Loading -> {
                Text(
                    text = "Loading trips...",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            is TripDataStatusUIState.Success -> {
                if (dataStatus.data.isNotEmpty()) {
                    LazyColumn(
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentPadding = PaddingValues(bottom = 152.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(dataStatus.data) { trip ->
                            TripCard(
                                title = trip.name,
                                travellers = trip.travellers,
                                startDate = tripsViewModel.formatDate(trip.startDate),
                                endDate = tripsViewModel.formatDate(trip.endDate),
                                modifier = Modifier.padding(bottom = 12.dp),
                                onCardClick = {
                                    // TODO: Implementasi aksi klik kartu
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "No trips available.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }

            is TripDataStatusUIState.Failed -> {
                Text(
                    text = "Error loading trips: ${dataStatus.errorMessage}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            else -> Unit
        }
    }
}


@Preview
    (showBackground = true,
            showSystemUi = true)
@Composable
fun YourTripPreview(){
    YourTripView(
        navController = rememberNavController(),
        token = "f57031c8-1c62-4bea-947b-4239db58e31c",
        tripsViewModel = viewModel(factory = TripsViewModel.Factory),
        context = LocalContext.current


    )
}