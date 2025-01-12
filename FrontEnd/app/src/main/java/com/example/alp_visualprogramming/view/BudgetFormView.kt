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
import com.example.alp_visualprogramming.viewModel.BudgetFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel

@Composable
fun BudgetFormView(budgetFormViewModel: BudgetFormViewModel, journeyViewModel: JourneyViewModel, context: Context, navController: NavController, token: String, modifier: Modifier, itineraryId: Int) {

    val submissionStatus = budgetFormViewModel.submissionStatus

    LaunchedEffect(budgetFormViewModel.submissionStatus) {
        val dataStatus = budgetFormViewModel.submissionStatus
        if (dataStatus is StringDataStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            budgetFormViewModel.clearDataErrorMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
        val submissionStatus = budgetFormViewModel.submissionStatus
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.cityheader),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(240.dp),

                alignment = Alignment.TopStart,
            )
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

            FormNumberField(
                inputValue = budgetFormViewModel.accommodation,
                onValueChange = {
                    budgetFormViewModel.changeAccommodationInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Accommodation",
                placeholderText = "Input Cost",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.transport,
                onValueChange = {
                    budgetFormViewModel.changeTransportInput(it)
                   budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Transport",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.entertainmentShopping,
                onValueChange = {
                    budgetFormViewModel.changeEntertainmentShoppingInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Shopping & Entertainment",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.culinary,
                onValueChange = {
                    budgetFormViewModel.changeCulinaryInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Culinary",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.sightSeeing,
                onValueChange = {
                    budgetFormViewModel.changeSightSeeingInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Sight Seeing",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.healthcare,
                onValueChange = {
                    budgetFormViewModel.changeHealthcareInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Healthcare",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            FormNumberField(
                inputValue = budgetFormViewModel.sport,
                onValueChange = {
                    budgetFormViewModel.changeSportInput(it)
                    budgetFormViewModel.checkNullFormValues()
                },
                modifier = Modifier.fillMaxWidth(),
                labelText = "Sport",
                placeholderText = "Input Budget",
                minLine = 1,
                maxLine = 1
            )
            if (!budgetFormViewModel.isCreate) {
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
                        text = if (budgetFormViewModel.isUpdate) "Update" else "Create",
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
                        Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                            if (budgetFormViewModel.isUpdate) {
                                Button(modifier = Modifier
                                    .padding(top = 10.dp)
                                    .width(372.dp)
                                    .height(62.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        Color(0xFFEE417D)
                                    ),
                                    onClick = {
                                        budgetFormViewModel.updateBudget(
                                            token = token,
                                            navController = navController,
                                        )
                                        navController.navigate("Journey/$itineraryId")

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

                            } else {
                                Button(modifier = Modifier
                                    .padding(top = 10.dp)
                                    .width(372.dp)
                                    .height(62.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        Color(0xFFEE417D)
                                    ),
                                    onClick = {
                                        budgetFormViewModel.createBudget(
                                            token = token,
                                            navController = navController,
                                            itineraryId = itineraryId
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
                            }

                        }
                    }


                }





            }

        }







    }

}





@Composable
@Preview
fun BudgetFormPreview() {
    BudgetFormView(
        budgetFormViewModel = viewModel<BudgetFormViewModel>(factory = BudgetFormViewModel.Factory),
        journeyViewModel = viewModel<JourneyViewModel>(factory = JourneyViewModel.Factory),
        context = LocalContext.current,
        navController = rememberNavController(),
        token = "",
        modifier = Modifier,
        itineraryId = 0

    )
}