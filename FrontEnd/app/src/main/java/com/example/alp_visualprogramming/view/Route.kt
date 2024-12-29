package com.example.alp_visualprogramming.view

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.BottomNavigationItem
import com.example.alp_visualprogramming.viewModel.TripsViewModel

@Composable
fun ItineraryApp (
    navController: NavHostController = rememberNavController(),
    tripsViewModel: TripsViewModel =  viewModel(factory = TripsViewModel.Factory)
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
                    YourTripView(modifier = Modifier.padding(innerPadding), navController = navController, token = "f57031c8-1c62-4bea-947b-4239db58e31c", tripsViewModel = tripsViewModel, context = localContext)
                }

                composable("Explore"){
                    ActivityDetailView(modifier = Modifier.padding(innerPadding))
                }

                composable("Profile"){
                    
                }

                composable("Activities"){
                    ActivitiesView(modifier = Modifier.padding(innerPadding))
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
