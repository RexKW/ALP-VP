package com.example.alp_visualprogramming.models

data class ItineraryModel(
    val id: Int,
    val name : String,
    val travellers: Int,
    val from: String,
    val to: String,


)

data class CreatedItineraryModel(
    val id: Int,
    val name : String,
    val createdDate: Int,
    val updatedDate: String,

)

data class ExploreItineraryModel(
    val id: Int,
    val name: String,
    val destinations: Int
)

data class ExploreItineraryResponse(
    val data: List<ExploreItineraryModel>
)

data class GetCreatedItineraryResponse(
    val data: CreatedItineraryModel
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