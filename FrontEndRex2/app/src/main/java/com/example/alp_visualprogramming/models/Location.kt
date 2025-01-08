package com.example.alp_visualprogramming.models

data class Location(
    val place_id: String,  // Unique identifier from the API
    val name: String,
    val address: String,
    val categories: List<String>,
    val openingHours: String?,
    val website: String?,
    val phone: String?
)