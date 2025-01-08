package com.example.alp_visualprogramming.models

import java.util.Date

data class ItineraryDestinationModel (
    val id: Int,
    var name: String? = null,
    val destination_id : Int,
    val accomodation_id : Int?,
    val start_date: String,
    val end_date: String

)

data class ItineraryDestinationRequest (
    var itinerary_id: Int,
    val accomodation_id : Int?,
    val start_date: String,
    val end_date: String

)


data class GetAllItineraryDestinationResponse(
    val data: List<ItineraryDestinationModel>
)