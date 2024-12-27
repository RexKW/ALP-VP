//package com.example.alp_visualprogramming.view
//
//import androidx.compose.foundation.gestures.ScrollableDefaults
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.alp_visualprogramming.view.template.TripCard
//
//@Composable
//fun YourTripView(){
//    if (dataStatus.data.isNotEmpty()) {
//        LazyColumn(
//            flingBehavior = ScrollableDefaults.flingBehavior(),
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//                .clip(RoundedCornerShape(10.dp))
//        ) {
//            items(dataStatus.data) { trip ->
//                TripCard (
//                    title = trip.title,
//                    travellers = trip.travellers,
//                    startDate = trip.startDate,
//                    endDate = trip.endDate,
//                    modifier = Modifier
//                        .padding(bottom = 12.dp),
//                    onCardClick = {
//                        TripDetailViewModel.getTodo(token, trip.id, navController, false)
//                    }
//                )
//            }
//        }
//    } else {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "No Task Found!",
//                fontSize = 21.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}