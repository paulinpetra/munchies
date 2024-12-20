package com.example.munchies.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenStatusResponse(
    @SerialName("is_currently_open")
    val isCurrentlyOpen: Boolean,
    @SerialName("restaurant_id")
    val restaurantId: String
)