package com.example.munchies.view

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.munchies.api.RestaurantsResponse
import com.example.munchies.model.RestaurantRepository
import com.example.munchies.viewmodel.RestaurantDetailViewModel
import com.example.munchies.viewmodel.RestaurantDetailViewModelFactory

@Composable
fun RestaurantDetailScreen(
    restaurantId: String,
    restaurantRepository: RestaurantRepository
) {
    // Use the ViewModel factory to create the ViewModel
    val viewModel: RestaurantDetailViewModel = viewModel(
        factory = RestaurantDetailViewModelFactory(restaurantRepository)
    )

    LaunchedEffect(restaurantId) {
        viewModel.getOpenStatus(restaurantId)
    }

    val openStatus = viewModel.openStatus.observeAsState()
    val error = viewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Restaurant ID: $restaurantId")

        openStatus.value?.let {
            Text(text = if (it.isCurrentlyOpen) "Open" else "Closed")
        }

        error.value?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}