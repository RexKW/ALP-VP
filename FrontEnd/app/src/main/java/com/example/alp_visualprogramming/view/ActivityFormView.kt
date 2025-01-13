package com.example.alp_visualprogramming.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.example.alp_visualprogramming.view.template.FormDescriptionField
import com.example.alp_visualprogramming.view.template.FormDropdown
import com.example.alp_visualprogramming.view.template.FormNumberField
import com.example.alp_visualprogramming.view.template.FormTextField
import com.example.alp_visualprogramming.view.template.FormTimePicker
import com.example.alp_visualprogramming.view.template.LocationCard
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.ActivitiesViewModel
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel
import com.example.alp_visualprogramming.viewModel.LocationViewModel

@Composable
fun ActivityFormView(activityFormViewModel: ActivityFormViewModel, activitiesViewModel: ActivitiesViewModel,locationViewModel: LocationViewModel, context: Context, navController: NavController, token: String, modifier: Modifier) {
    val activityFormUIState = activityFormViewModel.activityFormUIState.collectAsState()
    val submissionStatus = activityFormViewModel.submissionStatus
    val selectedLocation = activityFormViewModel.selectedLocation.collectAsState() // Observe selected location

    LaunchedEffect(activityFormViewModel.submissionStatus) {
        val dataStatus = activityFormViewModel.submissionStatus
        if (dataStatus is StringDataStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            activityFormViewModel.clearDataErrorMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
        val submissionStatus = activityFormViewModel.submissionStatus
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.cityheader),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(240.dp),

                alignment = Alignment.TopStart,
            )
            IconButton(
                onClick = {
                    activityFormViewModel.resetSelectedLocation() // Reset the selected location
                    navController.popBackStack() // Navigate back
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
                .verticalScroll(rememberScrollState())
                .padding(start = 32.dp, top = 5.dp, end = 32.dp, bottom = 140.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row {
                FormTextField(
                    inputValue = activityFormViewModel.titleInput,
                    onValueChange = {
                        activityFormViewModel.changeTitleInput(it)
                        activityFormViewModel.checkNullFormValues()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    labelText = "Activity Name",
                    placeholderText = "Input Activity Name",
                    minLine = 1,
                    maxLine = 1
                )

                if (activityFormViewModel.activityId != 0){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .fillMaxWidth()
                            .clickable{
                                activityFormViewModel.deleteActivity( token)
                                activitiesViewModel.getAllActivities(dayId =  activityFormViewModel.currDayId, token = token, navController = navController)
                            },
                        tint = Color(0xFFEE417D)
                    )
                }

            }



            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FormDropdown(
                    dropdownMenuExpanded = activityFormUIState.value.typeDropdownExpandedValue,
                    onDropdownMenuBoxExpandedChange = {
                        activityFormViewModel.changeTypeExpandedValue(it)
                    },
                    onDismissRequest = {
                        activityFormViewModel.dismissStatusDropdownMenu()
                    },
                    dropdownItems = listOf("Transport", "Shopping/Entertainment", "Sightseeing", "Food", "Healthcare", "Sport"),
                    onDropdownItemClick = {
                        activityFormViewModel.changeTypeInput(it)
                        activityFormViewModel.updateCategoriesBasedOnType(it) // Update categories based on the selected type
                        activityFormViewModel.resetSelectedLocation() // Reset the selected location
                        activityFormViewModel.checkNullFormValues()
                    },
                    selectedItem = activityFormViewModel.typeInput,
                    labelText = "Type"

                )
                FormNumberField(
                    inputValue = activityFormViewModel.costInput,
                    onValueChange = {
                        activityFormViewModel.changeCostInput(it)
                        activityFormViewModel.checkNullFormValues()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    labelText = "Cost",
                    placeholderText = "Input Cost",
                    minLine = 1,
                    maxLine = 1
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    FormTimePicker(
                        timePickerValue = activityFormViewModel.startTimeInput,
                        showTimeDialog = {activityFormViewModel.showTimePickerDialog(activityFormViewModel.initStartTimePickerDialog(context))},
                        labelText = "Start Time"
                    )
                    FormTimePicker(
                        timePickerValue = activityFormViewModel.endTimeInput,
                        showTimeDialog = {activityFormViewModel.showTimePickerDialog(activityFormViewModel.initEndTimePickerDialog(context))},
                        labelText = "End Time"
                    )
                }
                Column {
                    Text("Location",
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF3E122B),
                        )
                    )
                    Box(
                        modifier = Modifier
                            .width(163.dp)
                            .height(151.dp)
                            .background(
                                color = Color(0xFF5FEEDB),
                                shape = RoundedCornerShape(size = 21.dp)
                            )
                            .then(
                                if (activityFormViewModel.typeInput.isNotEmpty()) {
                                    Modifier.clickable {
                                        locationViewModel.clearLocations()
                                        navController.navigate(
                                            "LocationList/${activityFormViewModel.currDestination}/${activityFormViewModel.currentCategories.value}"
                                        )
                                    }
                                } else Modifier // Disable click if no type selected
                            )
                    ){
                        // Image placed outside the padding
                        Image(
                            painter = painterResource(id = R.drawable.bglocation), // Use placeholder image
                            contentDescription = "Background Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(71.88004.dp)
                                .align(Alignment.BottomCenter) // Align the image at the bottom center
                        )

                        // Content with padding
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp) // Padding applied only to the content
                        ) {
                            val selectedLocation = activityFormViewModel.selectedLocation.collectAsState()
                            if (selectedLocation.value != null) {
                                Column {
                                    Text(
                                        text = selectedLocation.value?.name ?: "No Location",
                                        style = TextStyle(
                                            fontSize = 32.sp,
                                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFBF7E7),
                                        )
                                    )
                                    Text(
                                        text = selectedLocation.value?.address ?: "No Address",
                                        style = TextStyle(
                                            fontSize = 10.57.sp,
                                            fontFamily = FontFamily(Font(R.font.tajawal_regular)),
                                            fontWeight = FontWeight(500),
                                            color = Color(0xFF440215),
                                        )
                                    )
                                }
                            } else {
                                Text(
                                    text = "Select Location",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.tajawal_regular)),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

            }
            FormDescriptionField(
                inputValue = activityFormViewModel.descriptionInput,
                onValueChange = {
                    activityFormViewModel.changeDescriptionInput(it)
                    activityFormViewModel.checkNullFormValues()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                labelText = "Description",
                placeholderText = "Input Description",
                minLine = 1,
                maxLine = 50
            )

            if (!activityFormViewModel.isCreate) {
                Button(
                    modifier = Modifier
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
                        text = if (activityFormViewModel.activityId != 0) "Update" else "Create",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),

                            )

                    )
                }
            } else {
                when (submissionStatus) {
                    is StringDataStatusUIState.Loading -> {

                    }

                    else -> {
                        if(activityFormViewModel.activityId != 0){
                            Button(modifier = Modifier
                                .padding(top = 10.dp)
                                .width(372.dp)
                                .height(62.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFFEE417D)
                                ),
                                onClick = {
                                    activityFormViewModel.updateActivity(
                                        token = token,
                                        navController = navController,
                                    )

                                    activitiesViewModel.getAllActivities(dayId =  activityFormViewModel.currDayId, token = token, navController = navController)
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
                            Button(modifier = Modifier
                                .padding(top = 10.dp)
                                .width(372.dp)
                                .height(62.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFFEE417D)
                                ),
                                onClick = {
                                    activityFormViewModel.deleteActivity(
                                        token = token,
                                    )

                                    activitiesViewModel.getAllActivities(dayId =  activityFormViewModel.currDayId, token = token, navController = navController)
                                }
                            ) {


                                Text(
                                    text = "Delete",
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
                                    val selectedLocation = activityFormViewModel.selectedLocation.value
                                    val placeId = selectedLocation?.place_id ?: ""
                                    val categories = activityFormViewModel.currentCategories.value

                                    if (placeId.isNotEmpty() && categories.isNotEmpty()) {
                                        if (selectedLocation != null) {
                                            activityFormViewModel.createActivity(
                                                token = token,
                                                navController = navController,
                                                placeId = placeId,
                                                categories = categories,
                                                name = selectedLocation.name,
                                                address = selectedLocation.address,
                                                openingHours = selectedLocation.openingHours,
                                                website = selectedLocation.website,
                                                phone = selectedLocation.phone
                                            )
                                        }
                                        activitiesViewModel.getAllActivities(
                                            dayId = activityFormViewModel.currDayId,
                                            token = token,
                                            navController = navController
                                        )
                                    } else {
                                        Toast.makeText(context, "Please select a location and activity type", Toast.LENGTH_SHORT).show()
                                    }
                                }
//                                onClick = {
//                                    activityFormViewModel.createActivity(
//                                        token = token,
//                                        navController = navController
//
//                                    )
//                                }
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
                        }

                    }
                }


            }

        }




    }

}





//@Composable
//fun ActivityFormPreview() {
//    ActivityFormView(
//        activityFormViewModel = viewModel(factory = ActivityFormViewModel.Factory),
//        context = LocalContext.current,
//        navController = rememberNavController(),
//        token = "",
//        modifier = Modifier,
//        activitiesViewModel = viewModel(factory = ActivitiesViewModel.Factory)
//    )
//}