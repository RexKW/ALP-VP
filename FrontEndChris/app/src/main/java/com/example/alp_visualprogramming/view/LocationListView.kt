package com.example.alp_visualprogramming.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_visualprogramming.viewModel.LocationViewModel
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.view.template.LocationCard

@Composable
fun LocationListView(navController: NavController, viewModel: LocationViewModel, cityName: String, categories: String) {
    val locations = viewModel.locationList.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val isLoading = locations.isEmpty() && errorMessage == null
    val searchText = remember { mutableStateOf("") }

    LaunchedEffect(cityName, categories) {
        viewModel.fetchPlaceIdForCity(cityName, apiKey = "c9b67c73febc4f71baad197651185143") { placeId ->
            if (placeId != null) {
                viewModel.fetchLocations(
                    categories = categories, // Use dynamic categories
                    filter = "place:$placeId",
                    apiKey = "c9b67c73febc4f71baad197651185143"
                )
            } else {
                Log.e("LocationListView", "Failed to fetch place_id for city: $cityName")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7)) // Background color
    ) {
        // Backdrop image
        Image(
            painter = painterResource(R.drawable.backdrop),
            contentDescription = "Backdrop image",
            modifier = Modifier
                .fillMaxWidth()
                .height(556.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Box with background image and back arrow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
            ) {
                // Background image
                Image(
                    painter = painterResource(id = R.drawable.cityheader),
                    contentDescription = "Background image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Back button
                Image(
                    painter = painterResource(id = R.drawable.arrowbackalp),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(1.dp)
                        .offset(x = 14.dp, y = 10.dp)
                        .align(Alignment.TopStart)
                        .clickable { navController.popBackStack() } // Back navigation
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = "Location",
                    style = TextStyle(
                        fontSize = 65.3.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D)
                    ),
                    modifier = Modifier
                        .width(322.dp)
                        .height(90.dp)
                )

                // Search Bar
                SearchBar(searchText.value) { newText ->
                    searchText.value = newText
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Content based on state
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Loading...", fontSize = 18.sp, color = Color.Gray)
                        }
                    }
                    errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            items(locations.filter {
                                it.name.contains(searchText.value, ignoreCase = true)
                            }) { location ->
                                LocationCard(location = location) {
                                    // Navigate to LocationDetailView with location ID
                                    navController.navigate("LocationDetail/${location.place_id}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Composable
//fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
//    Box(
//        modifier = Modifier
//            .width(380.dp)
//            .height(47.dp)
//            .background(color = Color(0xFFEAE5D2), shape = RoundedCornerShape(size = 20.dp))
//            .padding(horizontal = 16.dp, vertical = 12.dp)
//    ) {
//        BasicTextField(
//            value = searchText,
//            onValueChange = onSearchTextChange,
//            modifier = Modifier.fillMaxWidth(),
//            textStyle = TextStyle(
//                color = Color.Black,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal
//            ),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(onSearch = {
//                // Handle search action (currently placeholder)
//            })
//        )
//        if (searchText.isEmpty()) {
//            Text(
//                text = "     Search",
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight(400),
//                    color = Color(0x6B3E122B),
//                )
//            )
//        }
//    }
//}
