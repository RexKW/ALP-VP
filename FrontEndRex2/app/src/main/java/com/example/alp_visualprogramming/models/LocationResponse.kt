package com.example.alp_visualprogramming.models

data class LocationResponse(
    val features: List<Feature>
)

data class Feature(
    val properties: Properties
)

data class Properties(
    val name: String?,  // Name of the location
    val formatted: String?,  // Formatted address
    val categories: List<String>?,  // Categories of the location
    val website: String?,  // Website URL
    val opening_hours: String?,  // Opening hours
    val contact: Contact?,  // Contact details
    val place_id: String?  // Unique identifier for the place
)

data class Contact(
    val phone: String?  // Phone number
)
