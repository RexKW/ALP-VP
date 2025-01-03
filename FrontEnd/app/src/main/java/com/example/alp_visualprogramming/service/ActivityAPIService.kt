package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.ActivityRequest
import com.example.alp_visualprogramming.models.GetActivityResponse
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityAPIService{
    @GET("activities/allActivities/{dayId}")
    fun getAllActivities(@Header("X-API-TOKEN") token: String, @Path("dayId") dayId: Int): Call<GetAllActivityResponse>

    @GET("activities/{activityId}")
    fun getActivity(@Header("X-API-TOKEN") token: String, @Path("activityId") acitivityId: Int): Call<GetActivityResponse>

    @GET("activities/allDays/{itineraryDestinationId}")
    fun getAllDays(@Header("X-API-TOKEN") token: String, @Path("itineraryDestinationId") itineraryDestinationId: Int): Call<GetAllDayResponse>

    @POST("activities/createActivity/{dayId}")
    fun createActivity(@Header("X-API-TOKEN") token: String, @Path("dayId") dayId: Int, @Body activityRequest: ActivityRequest): Call<GetActivityResponse>

}