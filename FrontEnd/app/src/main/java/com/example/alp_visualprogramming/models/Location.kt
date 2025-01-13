package com.example.alp_visualprogramming.models

import com.google.gson.annotations.SerializedName

data class Location(
    val id: Int, // Default value for id when not provided
    val place_id: String,  // Unique identifier from the API
    val name: String,
    val address: String,
    val categories: List<String>,
    val openingHours: String?,
    val website: String?,
    val phone: String?
)
//data class Location(
//    @SerializedName("id") val id: Int, // Maps `id` from JSON to `id` in the class
//    @SerializedName("place_id") val placeId: String?,
//    @SerializedName("name") val name: String?,
//    @SerializedName("address") val address: String?,
//    @SerializedName("categories") val categories: List<String>?,
//    @SerializedName("opening_hours") val openingHours: String?,
//    @SerializedName("website") val website: String?,
//    @SerializedName("phone") val phone: String?
//)