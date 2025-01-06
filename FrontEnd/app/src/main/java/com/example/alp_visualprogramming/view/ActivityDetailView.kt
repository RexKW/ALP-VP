package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.ActivityDetailViewModel
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel

@Composable
fun ActivityDetailView(modifier: Modifier, activityDetailViewModel: ActivityDetailViewModel, activityFormViewModel: ActivityFormViewModel, navController: NavController, viewOnly: Boolean = false){

        Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
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
                        .align(Alignment.TopStart) // Align to the top left corner
                        .padding(16.dp)            // Add padding for proper spacing
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Use an arrow-back icon
                        contentDescription = "Back",
                        tint = Color.White // Set the icon color (adjust as needed for contrast)
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

                    Text(
                        text = activityDetailViewModel.currActivity!!.name,
                        style = TextStyle(
                            fontSize = 56.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),
                            textAlign = TextAlign.Start,
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFEE417D)).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBF7E7)
                            )
                            Text(
                                text = activityDetailViewModel.formatDate(
                                    activityDetailViewModel.parseDate(activityDetailViewModel.currActivity!!.start_time)!!,
                                    "HH:mm"
                                ) + " - " + activityDetailViewModel.formatDate(
                                    activityDetailViewModel.parseDate(activityDetailViewModel.currActivity!!.end_time)!!,
                                    "HH:mm"
                                ), style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),

                                    )
                            )
                        }
                        Row(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF5FEEDB)).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBF7E7)
                            )
                            Text(
                                text = "Djuanda Airport",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFEE417D),

                                    )
                            )
                        }
                        Row(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFEE417D)).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBF7E7)
                            )
                            Text(
                                text = activityDetailViewModel.currActivity!!.type, style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),

                                    )
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(top = 16.dp).clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF94284D)).fillMaxWidth().padding(20.dp)
                    )

                    {
                        Text(
                            "Description", style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )
                        )
                        Text(
                            text = activityDetailViewModel.currActivity!!.description,
                            modifier = Modifier.padding(top = 10.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.tajawal_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )
                        )

                    }
                    Row(
                        modifier = Modifier.padding(top = 16.dp).clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth().background(Color(0xFF5FEEDB)).padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color(0xFFFBF7E7),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text(
                            text = "Cost",
                            modifier = Modifier.padding(start = 10.dp),
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )

                        )
                        Text(
                            text = "Rp " + "${activityDetailViewModel.currActivity!!.cost}",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFEE417D),

                                ),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    if(!viewOnly){
                        Row(
                            modifier = Modifier.padding(top = 16.dp).clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth().background(Color(0xFFEE417D)).padding(10.dp).
                            clickable{
                                activityFormViewModel.initializeUpdate( activity = activityDetailViewModel.currActivity!!, navController = navController, dayId =  activityDetailViewModel.currDayId!! )
                            },

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Edit",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }
        }
}




@Preview(
    showBackground = true,
    showSystemUi = true,
    )
@Composable
fun ActivityDetailPreview() {
    ActivityDetailView(
        modifier = Modifier,
        activityDetailViewModel = viewModel(factory = ActivityDetailViewModel.Factory),
        navController = rememberNavController(),

        activityFormViewModel = viewModel(factory = ActivityFormViewModel.Factory)
    )
}