package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.view.template.ActivityCardView
import com.example.alp_visualprogramming.view.template.DateCardView
import com.example.alp_visualprogramming.view.template.DestinationCard

@Composable
fun ActivitiesView(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(R.drawable.cityheader),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(225.dp),

                alignment = Alignment.TopStart,
            )

            IconButton(
                onClick = { /* Handle back action here */ },
                modifier = Modifier
                    .align(Alignment.TopStart) // Align to the top left corner
                    .padding(16.dp)            // Add padding for proper spacing
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Use an arrow-back icon
                    contentDescription = "Back",
                    tint = Color.White // Set the icon color (adjust as needed for contrast)
                )
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding( top = 60.dp), contentPadding = PaddingValues(start = 32.dp, end = 32.dp)) {
                items(5){
                    DateCardView()
                }

            }
        }
        Column(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
//            when(dataStatus){
//                is DestinationDataStatusUIState.Success -> {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        modifier = Modifier.fillMaxWidth().
//                        padding(vertical = 16.dp)
//                    ) {
//                        items(dataStatus.data) { destination ->
//                            DestinationCard(
//                                name = destination.name,
//                            )
//                        }
//                    }
//                }
//                DestinationDataStatusUIState.Loading -> {
//                    // Show loading indicator
//                }
//                is DestinationDataStatusUIState.Failed -> {
//                    // Show error message
//                    Text(text = dataStatus.errorMessage)
//                }
//                DestinationDataStatusUIState.Start -> {
//                    // Show start state
//                }
//            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(-5.dp)
            ) {
                items(8) {
                    ActivityCardView("Shopping", "15:00 - 18:00", onCardClick = {
                        navController.navigate("Activities")
                    })
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(),
                        contentAlignment = Alignment.BottomStart // Align the button to the bottom-left
                    ) {
                        Button(
                            onClick = { /* Handle button click */ },


                            shape = RoundedCornerShape(10.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEE417D)
                            )
                        ) {
                            Text(text = "+    Add New Activity", style = TextStyle(fontSize = 24.sp, color = Color.White),modifier = Modifier.padding(5.dp),)
                        }
                    }
                }

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ActivitiesPreview(){
    ActivitiesView()
}