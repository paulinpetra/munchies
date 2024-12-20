package com.example.munchies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.munchies.model.RestaurantRepository
import com.example.munchies.view.HomePage
import com.example.munchies.view.RestaurantDetailScreen
import com.example.munchies.viewmodel.HomeViewModel

@Composable
fun AppNavigation(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController() // Create NavController
    val restaurantRepository = RestaurantRepository() // Instantiate RestaurantRepository

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomePage(
                navController = navController, // Pass NavController to HomePage
                viewModel = homeViewModel // Pass HomeViewModel to HomePage
            )
        }
        composable("detail/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            restaurantId?.let {
                RestaurantDetailScreen(
                    restaurantId = it,
                    restaurantRepository = restaurantRepository
                )
            }
        }
    }
}