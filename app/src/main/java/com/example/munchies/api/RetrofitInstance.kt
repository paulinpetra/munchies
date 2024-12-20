package com.example.munchies.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

//The Retrofit instance. Use an object (singleton), it ensures only one instance of the Retrofit client is created and used throughout the application

object RetrofitInstance {
    private const val BASE_URL = "https://food-delivery.umain.io/api/v1/"
    // Define the content type for JSON - needed for the kotlinx converter
    private val contentType = "application/json".toMediaType()


    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()

    }
    val restaurantApi : RestaurantApi = getInstance().create(RestaurantApi::class.java)

}