package com.example.munchies.model

import android.util.Log
import com.example.munchies.api.FilterData
import com.example.munchies.api.FiltersResponse
import com.example.munchies.api.OpenStatusResponse
import com.example.munchies.api.RestaurantData
import com.example.munchies.api.RestaurantsResponse
import com.example.munchies.api.RetrofitInstance
import retrofit2.Response


//Use the Retrofit Instance here in the Repository

class RestaurantRepository {
    private val restaurantApi = RetrofitInstance.restaurantApi

    //the fetch for restaurant list
    suspend fun fetchRestaurantData() : List<RestaurantData>? {
        return try {
            val response: Response<RestaurantsResponse> = restaurantApi.getRestaurants()
            if (response.isSuccessful) {
                response.body()?.restaurants
            }else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        }
        catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            null
        }

    }
    //the fetch for the filter list by using id from restaurants
    suspend fun fetchFilterDetails(filterId: String): FilterData? {
        return try {
            val response = restaurantApi.getFilterDetails(filterId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            null
        }
    }

    //fetch the open status of restaurants
    suspend fun fetchOpenStatus(restaurantId: String): OpenStatusResponse? {
        return try {
            val response = restaurantApi.getOpenStatus(restaurantId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("RestaurantRepository", "Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Exception: ${e.message}")
            null
        }
    }

}