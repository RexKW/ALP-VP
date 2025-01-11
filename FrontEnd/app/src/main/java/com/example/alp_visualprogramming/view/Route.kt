package com.example.alp_visualprogramming.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.BottomNavigationItem
import com.example.alp_visualprogramming.viewModel.AuthenticationViewModel
import com.example.alp_visualprogramming.viewModel.TripsViewModel
import com.example.alp_visualprogramming.viewModel.AuthState
import com.example.alp_vp.view.RegistrationView

@Composable
fun ItineraryApp(
    navController: NavHostController = rememberNavController(),
    tripsViewModel: TripsViewModel = viewModel(factory = TripsViewModel.Factory),
    authViewModel: AuthenticationViewModel = viewModel()
) {
    val localContext = LocalContext.current
    val authState by authViewModel.authState.collectAsState()

    // Tentukan startDestination berdasarkan status login
    val startDestination = when (authState) {
        is AuthState.Authenticated -> "Home"
        else -> "Login"
    }

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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (startDestination != "Login" && startDestination != "Register") {
                    NavigationBar {
                        bottomNavigationItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.title) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(text = item.title) },
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
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                // Register Screen
                composable("Register") {
                    RegistrationView(
                        navController = navController

                    )
                }

                // Login Screen
                composable("Login") {
                    LoginView(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }

                // Home Screen
                composable("Home") {
                    YourTripView(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        token = "f57031c8-1c62-4bea-947b-4239db58e31c",
                        tripsViewModel = tripsViewModel,
                        context = localContext
                    )
                }

                // Explore Screen
                composable("Explore") {
                    ActivityDetailView(modifier = Modifier.padding(innerPadding))
                }

                // Profile Screen
                composable("Profile") {
                    // Add your Profile view here
                }

                // Activities Screen
                composable("Activities") {
                    ActivitiesView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthenticationViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Login Page", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Button(onClick = { authViewModel.login(email, password) }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("Register") }) {
            Text("Go to Register")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppRoutePreview() {
    ItineraryApp()
}
