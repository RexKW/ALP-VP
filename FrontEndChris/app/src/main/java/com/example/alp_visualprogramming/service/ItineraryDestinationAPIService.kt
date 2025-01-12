package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.models.GetItineraryDestinationResponse
import com.example.alp_visualprogramming.models.ItineraryDestinationRequest
import com.example.alp_visualprogramming.models.ItineraryDestinationUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface ItineraryDestinationAPIService {
    @GET("itinerary/allJourneys/{itineraryId}")
    fun getAllItineraryDestination(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int): Call<GetAllItineraryDestinationResponse>

    @GET("itinerary/journey/{itineraryDestinationId}")
    fun getItineraryDestination(@Path("itineraryDestinationId")itineraryDestinationId: Int, @Header("X-API-TOKEN")token: String): Call<GetItineraryDestinationResponse>

    @GET("destinations/{destinationId}")
    fun getDestinationById(@Path("destinationId")destinationId: Int, @Header("X-API-TOKEN")token: String): Call<GetDestinationResponse>

    @POST("itinerary/createJourney/{destinationId}")
    fun createItineraryDestination(@Header("X-API-TOKEN")token: String,@Path("destinationId")destinationId: Int, @Body itineraryDestinationModel: ItineraryDestinationRequest): Call<GeneralResponseModel>

    @PUT("itinerary/updateJourney/{itineraryDestinationId}")
    fun updateItineraryDestination(@Header("X-API-TOKEN")token: String,@Path("itineraryDestinationId")itineraryDestinationId: Int, @Body itineraryDestinationModel: ItineraryDestinationUpdateRequest): Call<GeneralResponseModel>


    @DELETE("itinerary/deleteJourney/{itineraryDestinationId}")
    fun deleteItineraryDestination(@Header("X-API-TOKEN")token: String,@Path("itineraryDestinationId")itineraryDestinationId: Int): Call<GeneralResponseModel>


}