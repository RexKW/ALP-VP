package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.models.GetCreatedItineraryResponse
import com.example.alp_visualprogramming.models.GetItineraryResponse
import com.example.alp_visualprogramming.models.ItineraryRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItineraryAPIService {
    @GET("itinerary/ownedTrips")
    fun getAllItinerary(@Header("X-API-TOKEN") token: String): Call<GetAllItineraryResponse>

    @GET("api/todo-list/{id}")
    fun getItinerary(@Header("X-API-TOKEN") token: String, @Path("id") id: Int): Call<GetItineraryResponse>

    @POST("itinerary/create")
    fun createItinerary(@Header("X-API-TOKEN") token: String, @Body itineraryModel: ItineraryRequest): Call<GetCreatedItineraryResponse>

    @PUT("api/todo-list/{id}")
    fun updateItinerary(@Header("X-API-TOKEN") token: String, @Path("id") id: Int, @Body itineraryModel: ItineraryRequest): Call<GeneralResponseModel>

    @DELETE("api/todo-list/{id}")
    fun deleteItinerary(@Header("X-API-TOKEN") token: String, @Path("id") todoId: Int): Call<GeneralResponseModel>
}