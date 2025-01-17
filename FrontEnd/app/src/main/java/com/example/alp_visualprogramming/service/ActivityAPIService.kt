package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.ActivityRequest
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetActivityResponse
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import com.example.alp_visualprogramming.models.LocationWrapper
import com.example.alp_visualprogramming.models.UpdateActivityRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivityAPIService{
    @GET("activities/allActivities/{dayId}")
    fun getAllActivities(@Header("X-API-TOKEN") token: String, @Path("dayId") dayId: Int): Call<GetAllActivityResponse>

    @GET("activities/{activityId}")
    fun getActivity(@Header("X-API-TOKEN") token: String, @Path("activityId") acitivityId: Int): Call<GetActivityResponse>

    @GET("activities/allDays/{itineraryDestinationId}")
    fun getAllDays(@Header("X-API-TOKEN") token: String, @Path("itineraryDestinationId") itineraryDestinationId: Int): Call<GetAllDayResponse>


    @POST("activities/createActivity/{dayId}")
    fun createActivity(@Header("X-API-TOKEN") token: String, @Path("dayId") dayId: Int, @Body activityRequest: ActivityRequest): Call<GeneralResponseModel>

    @DELETE("activities/deleteActivity/{activityId}")
    fun deleteActivity(@Header("X-API-TOKEN") token: String, @Path("activityId") activityId: Int): Call<GeneralResponseModel>


    @PUT("activities/updateActivity/{activityId}")
    fun updateActivity(@Header("X-API-TOKEN") token: String, @Path("activityId") activityId: Int, @Body activityRequest: UpdateActivityRequest): Call<GeneralResponseModel>

    @GET("locations/getOrCreate")
    fun getOrCreateLocation(
        @Header("X-API-TOKEN") token: String,
        @Query("place_id") placeId: String,
        @Query("categories") categories: String,
        @Query("name") name: String,
        @Query("address") address: String,
        @Query("openingHours") openingHours: String?,
        @Query("website") website: String?,
        @Query("phone") phone: String?
//        @Query("place_id") placeId: String,
//        @Query("categories") categories: String,
//        @Query("name") name: String,
//        @Query("address") address: String,
//        @Query("openingHours") openingHours: String?,
//        @Query("website") website: String?,
//        @Query("phone") phone: String?
    ): Call<LocationWrapper>

    @GET("locations/{locationId}")
    fun getLocationById(
        @Header("X-API-TOKEN") token: String,
        @Path("locationId") locationId: Int
    ): Call<LocationWrapper>
}