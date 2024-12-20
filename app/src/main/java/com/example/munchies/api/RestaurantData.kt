package com.example.munchies.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantData(
    @SerialName("delivery_time_minutes")
    val deliveryTimeMinutes: Int,
    val filterIds: List<String>,
    val id: String,
    @SerialName("image_url")
    val imageUrl: String,
    val name: String,
    val rating: Double
)

@Serializable
data class RestaurantsResponse(
    val restaurants: List<RestaurantData>
)