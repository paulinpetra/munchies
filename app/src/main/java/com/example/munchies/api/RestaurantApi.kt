package com.example.munchies.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApi {
    //endpoint for all restaurants
    @GET("restaurants")
    suspend fun getRestaurants() : Response<RestaurantsResponse>

    //endpoint for filters
    @GET("filter/{id}")
    suspend fun getFilterDetails(@Path("id") filterId: String): Response<FilterData>

   //endpoint for open status of restaurant
    @GET("open/{restaurantId}")
    suspend fun getOpenStatus(@Path("restaurantId") restaurantId: String): Response<OpenStatusResponse>


}