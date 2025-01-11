package com.example.alp_visualprogramming.models

data class DayModel(
    val id: Int,
    val itinerary_destination_id: Int,
    val date: String
)

data class GetAllDayResponse(
    val data: List<DayModel>
)

data class GetDayResponse(
    val data: DayModel
)