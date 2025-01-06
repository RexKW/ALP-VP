package com.example.alp_visualprogramming.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.example.alp_visualprogramming.view.template.JourneyCardView
import com.example.alp_visualprogramming.view.template.NewDestinationCard
import com.example.alp_visualprogramming.view.template.NewTripCard
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.ActivitiesViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel

@Composable
fun JourneyExploreView(modifier: Modifier,journeyViewModel: JourneyViewModel, journeyFormViewModel: JourneyFormViewModel,activitiesViewModel: ActivitiesViewModel, token: String, navController: NavController, itineraryId: Int,  context : Context){
    val dataStatus = journeyViewModel.dataStatus

    LaunchedEffect(token) {
        if (token != "Unknown") {
            journeyViewModel.getAllJourneys(token, itineraryId)
        }
    }
    LaunchedEffect(dataStatus) {
        if (dataStatus is JourneyDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            journeyViewModel.clearDataErrorMessage()
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
        val dataStatus = journeyViewModel.dataStatus
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(R.drawable.cityheader),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(240.dp),

                alignment = Alignment.TopStart,
            )

            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.backdrop),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(561.dp).align(Alignment.BottomCenter).padding(top = 50.dp),
            )
            Column(modifier = Modifier.padding(start = 32.dp, top = 5.dp, end = 32.dp)) {
                Row (verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "Journey",
                        style = TextStyle(
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "Budget",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF440215),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Row {
                        Text(
                            text = "Clone",
                            style = TextStyle(
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF440215),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }

                when (dataStatus) {
                    is JourneyDataStatusUIState.Success -> if (dataStatus.data.isNotEmpty()) {
                        LazyColumn(
                            flingBehavior = ScrollableDefaults.flingBehavior(),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentPadding = PaddingValues(bottom = 152.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(dataStatus.data) { journey ->
                                Log.d("DestinationName", "Received destination: $journey")
                                JourneyCardView(
                                    name = journey.name,
                                    startDate = journeyViewModel.formatDate(journey.start_date),
                                    endDate = journeyViewModel.formatDate(journey.end_date),
                                    modifier = Modifier
                                        .padding(bottom = 12.dp),
                                    onCardClick = {
                                        activitiesViewModel.getAllDaysInitial(journey.id, token, navController, itineraryId)
                                    },
                                    onEditClick = {
                                    },
                                    canEdit = journeyViewModel.canEdit
                                )
                            }

                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if(journeyViewModel.canEdit) {

                                NewDestinationCard(modifier = Modifier, onCardClick = {
                                    journeyFormViewModel.initializeFormDestination(
                                        navController,
                                        token,
                                        itineraryId,
                                        null,
                                        false
                                    )
                                })
                            }else{

                            }

                        }
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
fun JourneyExplorePreview() {
    JourneyView(navController = rememberNavController(), modifier = Modifier, journeyViewModel = viewModel(factory = JourneyViewModel.Factory), activitiesViewModel = viewModel(factory = ActivitiesViewModel.Factory),journeyFormViewModel = viewModel(factory = JourneyFormViewModel.Factory), itineraryId = 1, context = LocalContext.current , token = "")
}