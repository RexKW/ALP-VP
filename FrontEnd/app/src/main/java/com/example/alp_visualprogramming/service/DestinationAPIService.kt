package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DestinationAPIService {
    @GET("destinations/all")
    suspend fun getAllDestinations(@Header("X-API-TOKEN") token: String): GetAllDestinationResponse

    @GET("destinations/{id}")
    suspend fun getDestination(
        @Header("X-API-TOKEN") token: String,
        @Path("id") id: Int
    ): GetDestinationResponse
}
