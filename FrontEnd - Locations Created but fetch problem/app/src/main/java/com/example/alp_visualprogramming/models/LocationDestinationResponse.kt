package com.example.alp_visualprogramming.models

import com.google.gson.annotations.SerializedName

data class LocationDestinationResponse(
    @SerializedName("results") val results: List<Result>
)

data class Result(
    @SerializedName("place_id") val placeId: String
)
