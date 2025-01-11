package com.example.alp_visualprogramming.models

data class DestinationModel(
    val id: Int,
    val name : String,
    val province: String
)

data class GetAllDestinationResponse(
    val data: List<DestinationModel>
)

data class GetDestinationResponse(
    val data: DestinationModel
)