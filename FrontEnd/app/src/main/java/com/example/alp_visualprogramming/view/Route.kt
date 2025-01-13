package com.example.alp_visualprogramming.view

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
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
import com.example.alp_visualprogramming.viewModel.AuthenticationViewModel
import com.example.alp_visualprogramming.viewModel.BudgetFormViewModel
import com.example.alp_visualprogramming.viewModel.BudgetViewModel
import com.example.alp_visualprogramming.viewModel.DestinationViewModel
import com.example.alp_visualprogramming.viewModel.ExploreViewModel
import com.example.alp_visualprogramming.viewModel.InvitedTripsViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel
import com.example.alp_visualprogramming.viewModel.LocationViewModel
import com.example.alp_visualprogramming.viewModel.TripNameViewModel
import com.example.alp_visualprogramming.viewModel.TripsViewModel
import com.example.alp_vp.view.AccountScreen
import com.example.alp_vp.view.SignIn
import com.example.alp_vp.view.SignUp
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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
    budgetFormViewModel: BudgetFormViewModel = viewModel(factory = BudgetFormViewModel.Factory),
    budgetViewModel: BudgetViewModel = viewModel(factory = BudgetViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory()),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = com.example.alp_visualprogramming.viewModel.AuthenticationViewModel.Factory)
){
    val token = tripsViewModel.token.collectAsState()
    val selectedIconColor = Color(0xFFEE417D)
    val unselectedIconColor = Color.White
    val selectedBgColor = Color(0xFF1E3A8A)
    val unselectedBgColor = Color(0XFF94284D)
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
    Surface (modifier = Modifier.fillMaxSize(), color = Color(0XFF94284D)) {

        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                val currentRoute = navController.currentBackStackEntryFlow.collectAsState(null).value?.destination?.route
                if (currentRoute != "Login" && currentRoute != "Register"){
                    NavigationBar(containerColor = Color(0XFF94284D)) {
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
                                },
                                icon = {
                                    val iconColor = if (selectedItemIndex == index) selectedIconColor else unselectedIconColor
                                    Icon(
                                        imageVector = if (selectedItemIndex == index) item.icon else item.unselectedIcon,
                                        contentDescription = item.title,
                                        tint = iconColor
                                    )
                                },

                                )
                        }
                    }
                }

            }
        ) { innerPadding ->
            NavHost(navController = navController,
                startDestination =
                if(token.value != "Unknown" && token.value != "") {
                    "Home"
                } else {
                    "Login"
                }
                ){

                composable("Login") {
                    SignIn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        authenticationViewModel = authenticationViewModel,
                        navController = navController,
                        context = localContext
                    )
                }

                composable("Register") {
                    SignUp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        authenticationViewModel = authenticationViewModel,
                        navController = navController,
                        context = localContext
                    )
                }

                composable("Home",
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    }){
                    YourTripView(modifier = Modifier.padding(innerPadding), navController = navController, token = "f57031c8-1c62-4bea-947b-4239db58e31c", tripsViewModel = tripsViewModel, context = localContext, journeyViewModel = viewModel(factory = JourneyViewModel.Factory))
                }

                composable("Invited",
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    }){
                    InvitedTripView(navController = navController, token = "f57031c8-1c62-4bea-947b-4239db58e31c", invitedTripsViewModel = invitedTripsViewModel, journeyViewModel = journeyViewModel, context = localContext )
                }

                composable("Explore"){
                    ExploreView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, exploreViewModel = exploreViewModel, context = localContext, journeyViewModel = journeyViewModel)
                }

                composable("Explore/{itineraryId}"){

                        backStackEntry->
                    JourneyExploreView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = journeyViewModel, itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0, context = localContext, token ="f57031c8-1c62-4bea-947b-4239db58e31c", journeyFormViewModel =journeyFormViewModel, activitiesViewModel = activityViewModel, locationViewModel = locationViewModel)
                }



                composable("Destinations"){
                    DestinationSearchView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, destinationViewModel = destinationViewModel, journeyFormViewModel = journeyFormViewModel)
                }

                composable("Create"){
                    TripNameView(modifier = Modifier.padding(innerPadding), navController = navController,tripNameViewModel = tripNameViewModel, token = "f57031c8-1c62-4bea-947b-4239db58e31c", context = localContext, budgetFormViewModel = budgetFormViewModel)
                }

                composable("FormDestination"){

                   JourneyFormView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, journeyFormViewModel = journeyFormViewModel, destinationViewModel = destinationViewModel, context = localContext)
                }

                composable("FormBudget/{itineraryId}"){
                        backStackEntry ->
                    BudgetFormView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, budgetFormViewModel = budgetFormViewModel, journeyViewModel = journeyViewModel, context = localContext, itineraryId =backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0)
                }

                composable("Budget/{itineraryId}"){
                        backStackEntry ->
                    BudgetView(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        journeyViewModel = journeyViewModel,
                        itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0,
                        context = localContext,
                        token = "f57031c8-1c62-4bea-947b-4239db58e31c",
                        budgetFormViewModel = budgetFormViewModel,
                        budgetViewModel = budgetViewModel
                    )

                }

                composable(
                    "Journey/{itineraryId}",
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    }
                ) { backStackEntry ->
                    JourneyView(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        journeyViewModel = journeyViewModel,
                        itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0,
                        context = localContext,
                        token = "f57031c8-1c62-4bea-947b-4239db58e31c",
                        journeyFormViewModel = journeyFormViewModel,
                        activitiesViewModel = activityViewModel,
                        locationViewModel = locationViewModel
                    )
                }


                composable("Profile"){
                    AccountScreen()
                }

                composable("Activities/{dayId}",
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    }){
                        backStackEntry->
                        ActivitiesView(modifier = Modifier.padding(innerPadding), navController = navController,activityViewModel, context = localContext, token = "f57031c8-1c62-4bea-947b-4239db58e31c",dayId = backStackEntry.arguments?.getString("dayId")?.toIntOrNull() ?: 0, activityDetailViewModel = activityDetailViewModel, activityFormViewModel = activityFormViewModel, canEdit = true)
                }

                composable("ActivityDetail"){
                    ActivityDetailView(modifier = Modifier.padding(innerPadding), navController = navController,activityDetailViewModel = activityDetailViewModel, activityFormViewModel = activityFormViewModel, activitiesViewModel = activityViewModel, token = "f57031c8-1c62-4bea-947b-4239db58e31c")
                }

                composable("FormActivity") {
                    ActivityFormView(modifier = Modifier.padding(innerPadding), navController = navController, activityFormViewModel = activityFormViewModel, context = localContext, token = "f57031c8-1c62-4bea-947b-4239db58e31c", activitiesViewModel =  activityViewModel, locationViewModel = locationViewModel)
                }

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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppRoutePreview() {
    ItineraryApp()
}
