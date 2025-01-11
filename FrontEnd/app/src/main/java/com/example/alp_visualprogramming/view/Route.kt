package com.example.alp_visualprogramming.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.BottomNavigationItem
import com.example.alp_visualprogramming.viewModel.ActivitiesViewModel
import com.example.alp_visualprogramming.viewModel.ActivityDetailViewModel
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel
import com.example.alp_visualprogramming.viewModel.DestinationViewModel
import com.example.alp_visualprogramming.viewModel.ExploreViewModel
import com.example.alp_visualprogramming.viewModel.InvitedTripsViewModel
import com.example.alp_visualprogramming.viewModel.JourneyFormViewModel
import com.example.alp_visualprogramming.viewModel.JourneyViewModel
import com.example.alp_visualprogramming.viewModel.TripNameViewModel
import com.example.alp_visualprogramming.viewModel.TripsViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
    invitedTripsViewModel: InvitedTripsViewModel = viewModel(factory = InvitedTripsViewModel.Factory)
){
    val selectedIconColor = Color(0xFF5FEEDB)
    val unselectedIconColor = Color.White
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
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = "Home"){
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
                    JourneyExploreView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = journeyViewModel, itineraryId = backStackEntry.arguments?.getString("itineraryId")?.toIntOrNull() ?: 0, context = localContext, token ="f57031c8-1c62-4bea-947b-4239db58e31c", journeyFormViewModel =journeyFormViewModel, activitiesViewModel = activityViewModel)
                }



                composable("Destinations"){
                    DestinationSearchView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, destinationViewModel = destinationViewModel, journeyFormViewModel = journeyFormViewModel)
                }

                composable("Create"){
                    TripNameView(modifier = Modifier.padding(innerPadding), navController = navController,tripNameViewModel = tripNameViewModel, token = "f57031c8-1c62-4bea-947b-4239db58e31c", context = localContext)
                }

                composable("FormDestination"){

                   JourneyFormView(modifier = Modifier.padding(innerPadding), token = "f57031c8-1c62-4bea-947b-4239db58e31c", navController = navController, journeyFormViewModel = journeyFormViewModel, destinationViewModel = destinationViewModel, context = localContext)
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
                        activitiesViewModel = activityViewModel
                    )
                }


                composable("Profile"){
//                    JourneyView(modifier = Modifier.padding(innerPadding), navController = navController, journeyViewModel = viewModel(factory = com.example.alp_visualprogramming.viewModel.JourneyViewModel.Factory), itineraryId = 1, context = localContext, token ="f57031c8-1c62-4bea-947b-4239db58e31c")
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
                    ActivityFormView(modifier = Modifier.padding(innerPadding), navController = navController, activityFormViewModel = activityFormViewModel, context = localContext, token = "f57031c8-1c62-4bea-947b-4239db58e31c", activitiesViewModel =  activityViewModel)
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
