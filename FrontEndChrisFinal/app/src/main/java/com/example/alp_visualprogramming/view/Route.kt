package com.example.alp_visualprogramming.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp_visualprogramming.BottomNavigationItem
import com.example.alp_visualprogramming.models.AccommodationResponse
import com.example.alp_visualprogramming.viewModel.ActivitiesViewModel
import com.example.alp_visualprogramming.viewModel.ActivityDetailViewModel
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel
import com.example.alp_visualprogramming.viewModel.DestinationViewModel
import com.example.alp_visualprogramming.viewModel.ExploreViewModel
import com.example.alp_visualprogramming.viewModel.InvitedTripsViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel
import com.example.alp_visualprogramming.viewModel.LocationViewModel
import com.example.alp_visualprogramming.viewModel.TripNameViewModel
import com.example.alp_visualprogramming.viewModel.TripsViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ItineraryApp (
    navController: NavHostController = rememberNavController(),
    tripsViewModel: TripsViewModel =  viewModel(factory = TripsViewModel.Factory),
    journeyViewModel: JourneyViewModel = viewModel(factory = JourneyViewModel.Factory),
    journeyFormViewModel: JourneyFormViewModel = viewModel(factory = JourneyFormViewModel.Factory),
    tripNameViewModel: TripNameViewModel = viewModel(factory = TripNameViewModel.Factory),
    destinationViewModel: DestinationViewModel = viewModel(factory = DestinationViewModel.Factory),
    activityViewModel: ActivitiesViewModel = viewModel(factory = ActivitiesViewModel.Factory),
    activityDetailViewModel: ActivityDetailViewModel = viewModel(factory = ActivityDetailViewModel.Factory),
    activityFormViewModel: ActivityFormViewModel = viewModel(factory = ActivityFormViewModel.Factory),
    exploreViewModel: ExploreViewModel = viewModel(factory = ExploreViewModel.Factory),
    invitedTripsViewModel: InvitedTripsViewModel = viewModel(factory = InvitedTripsViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory())
){
    val localContext = LocalContext.current
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            title = "Explore",
            icon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
        ),
        BottomNavigationItem(
            title = "Profile",
            icon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )
    Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    bottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(item.title){
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.icon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = "Home"){
                composable("Home"){
                    YourTripView(modifier = Modifier.padding(innerPadding), navController = navController, token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", tripsViewModel = tripsViewModel, context = localContext, journeyViewModel = viewModel(factory = JourneyViewModel.Factory))
                }

                composable("Invited"){
                    InvitedTripView(navController = navController, token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", invitedTripsViewModel = invitedTripsViewModel, journeyViewModel = journeyViewModel, context = localContext )
                }

                composable("Explore"){
                    ExploreView(modifier = Modifier.padding(innerPadding), token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", navController = navController, exploreViewModel = exploreViewModel, context = localContext, journeyViewModel = journeyViewModel)
                }

                composable("Explore/{itineraryId}"){

                        backStackEntry->
                    JourneyExploreView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = journeyViewModel, itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0, context = localContext, token ="7ffe2e10-558b-45ab-b3f6-fb4693051b5f", journeyFormViewModel =journeyFormViewModel, activitiesViewModel = activityViewModel, locationViewModel = locationViewModel)
                }



                composable("Destinations"){
                    DestinationSearchView(modifier = Modifier.padding(innerPadding), token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", navController = navController, destinationViewModel = destinationViewModel, journeyFormViewModel = journeyFormViewModel)
                }

                composable("Create"){
                    TripNameView(modifier = Modifier.padding(innerPadding), navController = navController,tripNameViewModel = tripNameViewModel, token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", context = localContext)
                }

                composable("FormDestination"){

                   JourneyFormView(modifier = Modifier.padding(innerPadding), token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", navController = navController, journeyFormViewModel = journeyFormViewModel, destinationViewModel = destinationViewModel, context = localContext)
                }

                composable("Journey/{itineraryId}"){

                    backStackEntry->
                    JourneyView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = journeyViewModel, itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0, context = localContext, token ="7ffe2e10-558b-45ab-b3f6-fb4693051b5f", journeyFormViewModel =journeyFormViewModel, activitiesViewModel = activityViewModel, locationViewModel = locationViewModel)
                }


                composable("Profile"){
//                    JourneyView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = viewModel(factory = com.example.alp_visualprogramming.viewModel.JourneyViewModel.Factory), itineraryId = 1, context = localContext, token ="7ffe2e10-558b-45ab-b3f6-fb4693051b5f")
                }

                composable("Activities/{dayId}"){
                        backStackEntry->
                        ActivitiesView(modifier = Modifier.padding(innerPadding), navController = navController,activityViewModel, context = localContext, token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f",dayId = backStackEntry.arguments?.getString("dayId")?.toIntOrNull() ?: 0, activityDetailViewModel = activityDetailViewModel, activityFormViewModel = activityFormViewModel, canEdit = true)
                }

                composable("ActivityDetail"){
                    ActivityDetailView(modifier = Modifier.padding(innerPadding), navController = navController,activityDetailViewModel = activityDetailViewModel, activityFormViewModel = activityFormViewModel)
                }
//                composable("ActivityDetail/{activityId}/{locationId}", arguments = listOf(
//                    navArgument("activityId") { type = NavType.IntType },
//                    navArgument("locationId") { type = NavType.IntType }
//                )) { backStackEntry ->
//                    val activityId = backStackEntry.arguments?.getInt("activityId") ?: 0
//                    val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
//                    val token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f"
//
//                    // Fetch the location name for the given location ID
//                    activityDetailViewModel.fetchLocationName(token, locationId)
//
//                    // Navigate to the detail view with the activity and fetched location name
//                    ActivityDetailView(
//                        modifier = Modifier.padding(innerPadding),
//                        navController = navController,
//                        activityDetailViewModel = activityDetailViewModel,
//                        activityFormViewModel = activityFormViewModel
//                    )
//                }

                composable("FormActivity") {
                    ActivityFormView(modifier = Modifier.padding(innerPadding), navController = navController, activityFormViewModel = activityFormViewModel, context = localContext, token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f", activitiesViewModel =  activityViewModel, locationViewModel = locationViewModel,)
                }

                // Add the Location List route
                composable(
                    "LocationList/{cityName}/{categories}",
                    arguments = listOf(
                        navArgument("cityName") { type = NavType.StringType },
                        navArgument("categories") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
                    val categories = backStackEntry.arguments?.getString("categories") ?: ""

                    LocationListView(
                        navController = navController,
                        viewModel = locationViewModel,
                        cityName = cityName,
                        categories = categories
                    )
                }

                // Add the Location Detail route
                composable(
                    "LocationDetail/{place_id}",
                    arguments = listOf(navArgument("place_id") { type = NavType.StringType })
                ) { backStackEntry ->
                    val placeId = backStackEntry.arguments?.getString("place_id")
                    val location = locationViewModel.locationList.value.find { it.place_id == placeId }

                    if (location != null) {
                        LocationDetailView(
                            location = location,
                            navController = navController,
                            activityFormViewModel = activityFormViewModel // Pass the ViewModel here
                        )
                    } else {
                        Log.e("ItineraryApp", "Location with place_id $placeId not found.")
                    }
                }

                composable(
                    "HotelList/{cityName}/{journeyId}",
                    arguments = listOf(
                        navArgument("cityName") { type = NavType.StringType },
                        navArgument("journeyId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
                    val journeyId = backStackEntry.arguments?.getInt("journeyId") ?: 0

                    HotelListView(
                        navController = navController,
                        viewModel = locationViewModel,
                        cityName = cityName,
                        journeyId = journeyId // Pass the journeyId to HotelListView
                    )
                }

                composable(
                    "HotelDetail/{place_id}/{journeyId}",
                    arguments = listOf(
                        navArgument("place_id") { type = NavType.StringType },
                        navArgument("journeyId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val placeId = backStackEntry.arguments?.getString("place_id") ?: ""
                    val journeyId = backStackEntry.arguments?.getInt("journeyId") ?: 0
                    val location = locationViewModel.locationList.value.find { it.place_id == placeId }

                    if (location != null) {
                        HotelDetailView(location = location, navController = navController, journeyId = journeyId, journeyViewModel = journeyViewModel) // Pass the journeyId to HotelDetailView)
                    } else {
                        Log.e("HotelDetailView", "Location not found for place_id: $placeId")
                    }
                }
//
//                composable(
//                    "SelectedHotelDetailView/{accommodationId}",
//                    arguments = listOf(navArgument("accommodationId") { type = NavType.IntType })
//                ) { backStackEntry ->
//                    val accommodationId = backStackEntry.arguments?.getInt("accommodationId") ?: 0
//                    SelectedHotelDetailView(
//                        accommodationId = accommodationId,
//                        navController = navController,
//                        journeyViewModel = journeyViewModel
//                    )
//                }
                composable(
                    "SelectedHotelDetailView/{accommodationId}/{cityName}",
                    arguments = listOf(
                        navArgument("accommodationId") { type = NavType.IntType },
                        navArgument("cityName") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val accommodationId = backStackEntry.arguments?.getInt("accommodationId") ?: 0
                    val cityName = backStackEntry.arguments?.getString("cityName") ?: ""

                    // Fetch accommodation details before rendering
                    val accommodation = remember { mutableStateOf<AccommodationResponse?>(null) }
                    val token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f" // Replace with dynamic token if applicable

                    LaunchedEffect(accommodationId) {
                        journeyViewModel.viewModelScope.launch {
                            journeyViewModel.getAccommodationDetails(accommodationId) { result ->
                                accommodation.value = result
                            }
                        }
                    }

                    if (accommodation.value != null) {
                        SelectedHotelDetailView(
                            navController = navController,
                            accommodation = accommodation.value!!,
                            cityName = cityName
                        )
                    } else {
                        // Show a loading screen or fallback UI
                        Text(
                            text = "Loading accommodation details...",
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    }
                }

            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppRoutePreview() {
    ItineraryApp()
}
