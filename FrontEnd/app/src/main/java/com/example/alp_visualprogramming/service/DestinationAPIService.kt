package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DestinationAPIService {
    @GET("destinations/all")
    fun getAllDestinations(@Header("X-API-TOKEN") token: String): Call<GetAllDestinationResponse>
}