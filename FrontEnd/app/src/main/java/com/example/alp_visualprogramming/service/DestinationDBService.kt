package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.DestinationModel
import retrofit2.http.GET

interface DestinationDBService {
    @GET("destinations/all")
    fun getAllDestinations(): List<DestinationModel>
}