package com.example.munchies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchies.api.OpenStatusResponse
import com.example.munchies.model.RestaurantRepository
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    // LiveData to hold the open status of the restaurant
    private val _openStatus = MutableLiveData<OpenStatusResponse?>()
    val openStatus: LiveData<OpenStatusResponse?> = _openStatus

    // LiveData to hold error states
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Function to fetch the open status of a restaurant
    fun getOpenStatus(restaurantId: String) {
        viewModelScope.launch {
            try {
                val status = restaurantRepository.fetchOpenStatus(restaurantId)
                if (status != null) {
                    _openStatus.postValue(status)
                    _error.postValue(null)
                } else {
                    _error.postValue("Failed to fetch open status")
                }
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
                Log.e("RestaurantDetailViewModel", "Error fetching open status", e)
            }
        }
    }
}