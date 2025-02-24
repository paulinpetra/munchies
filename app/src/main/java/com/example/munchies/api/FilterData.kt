package com.example.munchies.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterData(
    val id: String,
    @SerialName("image_url")
    val imageUrl: String,
    val name: String
)

@Serializable
data class FiltersResponse(
    val filters: List<FilterData>
)