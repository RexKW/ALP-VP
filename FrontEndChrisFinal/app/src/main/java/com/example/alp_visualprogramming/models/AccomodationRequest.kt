

package com.example.alp_visualprogramming.models

data class AccommodationRequest(
    val id: Int? = null, // Add this field
    val place_id: String,
    val name: String,
    val address: String,
    val location_image: String,
    val place_api: String,
    val categories: List<String>,
    val cost: Double,
    val people: Int,
    val opening_hours: String? = null,
    val website: String? = null,
    val phone: String? = null
)