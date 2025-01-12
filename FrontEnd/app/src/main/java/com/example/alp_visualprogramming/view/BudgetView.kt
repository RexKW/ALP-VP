package com.example.alp_visualprogramming.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.example.alp_visualprogramming.view.template.JourneyCardView
import com.example.alp_visualprogramming.view.template.NewDestinationCard
import com.example.alp_visualprogramming.view.template.NoDestinationCard
import com.example.alp_visualprogramming.viewModel.ActivitiesViewModel
import com.example.alp_visualprogramming.viewModel.BudgetFormViewModel
import com.example.alp_visualprogramming.viewModel.BudgetViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel


@Composable
fun BudgetView(modifier: Modifier, journeyViewModel: JourneyViewModel, budgetFormViewModel: BudgetFormViewModel, budgetViewModel: BudgetViewModel, token: String, navController: NavController, itineraryId: Int, context : Context){
    val dataStatus = journeyViewModel.dataStatus

    LaunchedEffect(token) {
        if (token != "Unknown") {
            budgetViewModel.getAllBudget(token, itineraryId)
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
                    navController.navigate("Home")
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
                Row (verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Journey",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF440215),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate("Journey/$itineraryId")
                        }
                    )
                    Text(
                        text = "Budget",
                        style = TextStyle(
                            fontSize = 45.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "Travellers",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF440215),
                            textAlign = TextAlign.Center,
                        )
                    )
                }



                Row() {
                    val values = listOf(30f, 40f, 30f) // Percentage values for each segment
                    val colors = listOf(Color(0xFFEE417D), Color(0xFF440215), Color(0xFFFBF7E7))

                    Canvas(modifier = Modifier.width(150.dp).height(150.dp)) {
                        val total = values.sum()
                        var startAngle = 0f

                        values.forEachIndexed { index, value ->
                            val sweepAngle = (value / total) * 360f
                            drawArc(
                                color = colors[index],
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = true
                            )
                            startAngle += sweepAngle
                        }
                    }


                    Column(modifier= Modifier.padding(start = 16.dp)) {


                        Column {
                            Text(
                                text = "Total Budget",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF440215),

                                    )
                            )
                            Text(
                                text = "Rp " + budgetViewModel.planned.toString(),
                                style = TextStyle(
                                    fontSize = 40.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFEE417D),

                                    )
                            )
                        }
                        Column {
                            Text(
                                text = "Total Spending's",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF440215),

                                    )
                            )
                            Text(
                                "",
                                style = TextStyle(
                                    fontSize = 40.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFEE417D),

                                    )
                            )
                        }


                    }
                }

            }



        }
    }
}



@Preview
    (
            showBackground = true,
            showSystemUi = true

            )
@Composable
fun BudgetPreview(){
    BudgetView(
        modifier = Modifier,
        journeyViewModel = viewModel(factory = JourneyViewModel.Factory),
        budgetFormViewModel = viewModel(factory = BudgetFormViewModel.Factory),
        budgetViewModel = viewModel(factory = BudgetViewModel.Factory),
        token = "",
        navController = rememberNavController(),
        itineraryId = 0,
        context = LocalContext.current
    )
}