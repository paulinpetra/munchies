package com.example.munchies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.munchies.model.RestaurantRepository


//This factory pattern is used to inject dependencies (RestaurantRepository) into ViewModel instances. This is important for testing and maintaining separation of concerns.
//When you need a ViewModel in an activity or fragment, you use ViewModelProvider along with this factory to obtain an instance. This ensures that the ViewModel is created with the necessary dependencies.
class RestaurantDetailViewModelFactory(
    //The factory takes a RestaurantRepository as a parameter. This allows the ViewModel to receive the repository as a dependency.
    private val restaurantRepository: RestaurantRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //Check if the requested ViewModel class is RestaurantDetailViewMode
        if (modelClass.isAssignableFrom(RestaurantDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            //Create an instance of RestaurantDetailViewModel, passing the restaurantRepository to its constructor.
            return RestaurantDetailViewModel(restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}