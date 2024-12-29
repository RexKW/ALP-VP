package com.example.alp_visualprogramming.models

import com.google.gson.annotations.SerializedName

data class ItineraryModel(
    val id: Int,
    val name : String,
    val travellers: Int,
    val startDate: String,
    val endDate: String,


)

data class GetAllItineraryResponse(
    val data: List<ItineraryModel>
)

data class GetItineraryResponse(
    val data: ItineraryModel
)


data class ItineraryRequest(
    val name : String,


    )