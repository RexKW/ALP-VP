package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.view.template.DestinationCard
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.uiStates.DestinationDataStatusUIState
import com.example.alp_visualprogramming.viewModel.DestinationViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel

@Composable
fun DestinationSearchView(
    modifier: Modifier,
    navController: NavHostController,
    destinationViewModel: DestinationViewModel,
    journeyFormViewModel: JourneyFormViewModel,
    token: String
){
    LaunchedEffect(Unit) {
        destinationViewModel.getAllDestinations(token)
    }

    val dataStatus = destinationViewModel.dataStatus
    val searchText = remember { mutableStateOf("") }


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
        Column (modifier = Modifier.padding(start = 32.dp, top = 5.dp, end = 32.dp)) {
            Text(
                text = "Destinations",
                style = TextStyle(
                    fontSize = 64.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEE417D),
                    textAlign = TextAlign.Center,
                )
            )

            SearchBar(searchText.value) { newText ->
                searchText.value = newText
            }

            when(dataStatus){
                is DestinationDataStatusUIState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth().
                        padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(dataStatus.data.filter {
                            it.name.contains(searchText.value, ignoreCase = true) ||
                                    it.province.contains(searchText.value, ignoreCase = true)
                        }) { destination ->
                            DestinationCard(
                                name = destination.name,
                                province = destination.province,
                                onCardClick = {
                                    journeyFormViewModel.selectDestination(destination, navController)
                                }
                            )
                        }

                    }
                }
                DestinationDataStatusUIState.Loading -> {
                    // Show loading indicator
                }
                is DestinationDataStatusUIState.Failed -> {
                    // Show error message
                    Text(text = dataStatus.errorMessage)
                }
                DestinationDataStatusUIState.Start -> {
                    // Show start state
                }else -> {
                    Text(text = "No Destination Found")
                }
            }
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        modifier = Modifier.fillMaxWidth().
//                        padding(vertical = 16.dp),
//                        horizontalArrangement = Arrangement.spacedBy(10.dp),
//                        verticalArrangement = Arrangement.spacedBy(10.dp)
//                    ) {
//                        items(15){
//                            DestinationCard("Jakarta", "East Java")
//                            DestinationCard("Surabaya","East Java")
//                            DestinationCard("Makassar","East Java")
//                            DestinationCard("Samarinda","East Java")
//                        }
//
//                    }

        }
    }


}

@Composable

fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .width(380.dp)
            .height(47.dp)
            .background(color = Color(0xFFEAE5D2), shape = RoundedCornerShape(size = 20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                // Handle search action (currently placeholder)
            })
        )
        if (searchText.isEmpty()) {
            Text(
                text = "     Search",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x6B3E122B),
                )
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DestinationSearchPreview(){
    DestinationSearchView(
        modifier = Modifier,
        navController = rememberNavController(),
        token = "",
        destinationViewModel = viewModel(factory = DestinationViewModel.Factory),
        journeyFormViewModel = viewModel(factory = JourneyFormViewModel.Factory)
    )
}



