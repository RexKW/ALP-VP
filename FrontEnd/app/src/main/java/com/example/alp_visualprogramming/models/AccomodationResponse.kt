package com.example.alp_visualprogramming.models

data class AccommodationResponse(
    val id: Int,
    val name: String,
    val address: String,
    val location_image: String,
    val place_id: String,
    val place_api: String,
    val categories: List<String>,
    val cost: Double,
    val people: Int,
    val opening_hours: String?,
    val website: String?,
    val phone: String?,
)