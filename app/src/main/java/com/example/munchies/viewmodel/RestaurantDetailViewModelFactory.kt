package com.example.munchies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.munchies.model.RestaurantRepository

class RestaurantDetailViewModelFactory(
    private val restaurantRepository: RestaurantRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantDetailViewModel(restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}