package com.example.alp_visualprogramming.view

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.alp_visualprogramming.view.template.FormDatePickerEnd
import com.example.alp_visualprogramming.view.template.FormDatePickerStart
import com.example.alp_visualprogramming.viewModel.DestinationViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel

@Composable
fun JourneyFormView(modifier: Modifier, token: String, navController: NavController, journeyFormViewModel: JourneyFormViewModel, destinationViewModel: DestinationViewModel, context: Context){
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
                    journeyFormViewModel.resetViewModel()
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
                Row() {


                    Text(
                        text = "Journey",
                        style = TextStyle(
                            fontSize = 64.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),
                            textAlign = TextAlign.Center,
                        )
                    )

                    if(journeyFormViewModel.isUpdate){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .fillMaxWidth()
                                .clickable{
                                    journeyFormViewModel.deleteJourney(navController, token, journeyFormViewModel.currItinerarDestinationId?:0)
                                }
                        )
                    }


                }

                Card(
                    modifier = Modifier
                        .width(342.dp)
                        .height(210.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF5FEEDB),
                    ),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {
                        destinationViewModel.initializeDestinationItineraryId(journeyFormViewModel.currItineraryId!!, token, navController)
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.destinationscard),
                            modifier = Modifier.width(342.dp)
                                .height(98.38745.dp).align(Alignment.BottomStart),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.End),
                                tint = Color.White

                            )
                            Text(
                                text = journeyFormViewModel.locationInput?.name ?: "Select Destination",
                                modifier = Modifier.padding(top = 32.dp),
                                style = TextStyle(
                                    fontSize = 35.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),
                                    )
                            )
                            Text(
                                text = journeyFormViewModel.locationInput?.province ?: "",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF3E122B),
                                    )
                            )


                        }

                    }
                }
                FormDatePickerStart(
                        datePickerValue = journeyFormViewModel.startDateInput,
                    showCalendarDialog = {journeyFormViewModel.showDatePickerDialog(journeyFormViewModel.initStartDatePickerDialog(context))}
                )

                FormDatePickerEnd(
                    datePickerValue = journeyFormViewModel.endDateInput,
                    showCalendarDialog = {journeyFormViewModel.showDatePickerDialog(journeyFormViewModel.initEndDatePickerDialog(context))}
                )


                if (!journeyFormViewModel.isCreate) {
                    Button(modifier = Modifier
                        .padding(top = 10.dp)
                        .width(372.dp)
                        .height(62.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFEE417D),
                        ),
                        onClick = {},
                        enabled = false
                    ) {

                        Text(
                            text = if(journeyFormViewModel.isUpdate) "Update" else "Create",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                )

                        )
                    }
                }else{
                    if(!journeyFormViewModel.isUpdate) {


                        Button(modifier = Modifier
                            .padding(top = 10.dp)
                            .width(372.dp)
                            .height(62.dp),
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFEE417D)
                            ),
                            onClick = {
                                journeyFormViewModel.createJourney(
                                    token = token,
                                    locationId = journeyFormViewModel.locationInput?.id ?: 0,
                                    navController = navController
                                )
                            }
                        ) {

                            Text(
                                text = "Create",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),

                                    )

                            )
                        }
                    }else{
                        Button(modifier = Modifier
                            .padding(top = 10.dp)
                            .width(372.dp)
                            .height(62.dp),
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFEE417D)
                            ),
                            onClick = {
                                journeyFormViewModel.updateJourney(
                                    token = token,
                                    locationId = journeyFormViewModel.locationInput?.id ?: 0,
                                    navController = navController,
                                    journeyId = journeyFormViewModel.currItinerarDestinationId ?: 0
                                )
                            }
                        ) {

                            Text(
                                text = "Update",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFBF7E7),

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
    (showBackground = true,
            showSystemUi = true)
@Composable
fun DestinationDetailViewPreview(){
    JourneyFormView(modifier = Modifier,
        token = "",
        navController = rememberNavController(),
        journeyFormViewModel = viewModel(factory = JourneyFormViewModel.Factory),
        destinationViewModel = viewModel(factory = DestinationViewModel.Factory),
        context =   LocalContext.current
    )
}