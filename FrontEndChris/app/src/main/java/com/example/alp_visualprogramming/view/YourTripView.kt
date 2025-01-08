package com.example.alp_visualprogramming.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.example.alp_visualprogramming.view.template.NewTripCard
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.JourneyViewModel
import com.example.alp_visualprogramming.viewModel.TripsViewModel

@Composable
fun YourTripView(modifier: Modifier = Modifier,  navController : NavController, token: String, tripsViewModel: TripsViewModel, journeyViewModel: JourneyViewModel, context : Context){
    var placeholder = true
    val dataStatus = tripsViewModel.dataStatus
    LaunchedEffect(token) {
        if (token != "Unknown") {
            tripsViewModel.getAllItineraries(token)
        }
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is TripDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            tripsViewModel.clearDataErrorMessage()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.backdrop),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().height(556.dp).align(Alignment.BottomCenter),
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.align(Alignment.Start).padding(start = 15.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    "Your Trips",
                    style = TextStyle(
                        fontSize = 45.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D),

                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    "Invited Trips",
                    modifier = Modifier.padding(start = 30.dp),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E122B),

                        textAlign = TextAlign.Center,
                    )
                )
            }
            when (dataStatus) {
                is TripDataStatusUIState.Success ->
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
                                startDate = tripsViewModel.formatDate(trip.from),
                                endDate = tripsViewModel.formatDate(trip.to),
                                modifier = Modifier
                                    .padding(bottom = 12.dp),
                                onCardClick = {
                                    journeyViewModel.getJourney(trip.id, navController)
                                }
                            )
                        }
                        item {
                            NewTripCard(modifier = Modifier, onCardClick = {
                                navController.navigate("Create")
                            })
                        }
                    }

            }

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
        journeyViewModel = viewModel(factory = JourneyViewModel.Factory),
        context = LocalContext.current


    )
}