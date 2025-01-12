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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.uiStates.ExploreDataStatusUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.example.alp_visualprogramming.view.template.ExploreCardView
import com.example.alp_visualprogramming.view.template.NewTripCard
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.ExploreViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel

@Composable
fun ExploreView(modifier: Modifier, token: String, exploreViewModel: ExploreViewModel, journeyViewModel: JourneyViewModel, navController: NavController, context: Context){
    val dataStatus = exploreViewModel.dataStatus
    LaunchedEffect(token) {
        if (token != "Unknown") {
            exploreViewModel.getAllItineraries(token)
        }
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is ExploreDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            exploreViewModel.clearDataErrorMessage()
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
                    "Explore",
                    style = TextStyle(
                        fontSize = 45.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D),

                        textAlign = TextAlign.Center,
                    )
                )

            }
            when (dataStatus) {
                is ExploreDataStatusUIState.Success -> {
                    LazyColumn(
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentPadding = PaddingValues(bottom = 152.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(dataStatus.data) { trip ->
                            if(trip.destinations != 0) {
                                ExploreCardView(
                                    title = trip.name,
                                    destinations = trip.destinations,
                                    modifier = Modifier,
                                    onCardClick = {
                                        journeyViewModel.viewJourney(trip.id, navController)
                                    }
                                )
                            }

                        }

                    }
                }

                is ExploreDataStatusUIState.Loading -> {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                }
            }

        }

    }
}