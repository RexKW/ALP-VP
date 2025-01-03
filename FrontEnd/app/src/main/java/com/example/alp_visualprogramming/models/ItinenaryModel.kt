package com.example.alp_visualprogramming.models

import com.google.gson.annotations.SerializedName

data class ItineraryModel(
    val id: Int,
    val name : String,
    val travellers: Int,
    val from: String,
    val to: String,


)

data class createdItineraryModel(
    val id: Int,
    val name : String,
    val createdDate: Int,
    val updatedDate: String,

)

data class GetCreatedItineraryResponse(
    val data: createdItineraryModel
)

data class GetAllItineraryResponse(
    val data: List<ItineraryModel>
)

data class GetItineraryResponse(
    val data: ItineraryModel
)


data class ItineraryRequest(
    val name : String
)