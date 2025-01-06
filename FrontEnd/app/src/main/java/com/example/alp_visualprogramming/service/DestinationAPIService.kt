package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DestinationAPIService {
    @GET("destinations/all")
    fun getAllDestinations(@Header("X-API-TOKEN") token: String): Call<GetAllDestinationResponse>

    @GET("destinations/{id}")
    fun getDestination(@Header("X-API-TOKEN") token: String, @Path("id") id: Int): Call<GetDestinationResponse>
}