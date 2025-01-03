package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.ActivityRequest
import com.example.alp_visualprogramming.models.GetActivityResponse
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import com.example.alp_visualprogramming.service.ActivityAPIService
import retrofit2.Call

interface ActivityRepository {
    fun getAllActivities(token: String, dayId: Int): Call<GetAllActivityResponse>
    fun getActivity(token: String, activityId: Int): Call<GetActivityResponse>
    fun createActivity(token: String, dayId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int): Call<GetActivityResponse>
    fun getAllDays(token: String, itinerary_destination_Id: Int): Call<GetAllDayResponse>
}

class NetworkActivityRepository(
    private val activityAPIService: ActivityAPIService
): ActivityRepository {
    override fun getAllActivities(token: String, dayId: Int): Call<GetAllActivityResponse> {
        return activityAPIService.getAllActivities(token, dayId)
    }

    override fun getAllDays(token: String, itinerary_destination_Id: Int): Call<GetAllDayResponse> {
        return activityAPIService.getAllDays(token, itinerary_destination_Id)
    }

    override fun getActivity(token: String, activityId: Int): Call<GetActivityResponse> {
        return activityAPIService.getActivity(token, activityId)

    }

    override fun createActivity(token: String, dayId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int): Call<GetActivityResponse> {
        return activityAPIService.createActivity(token, dayId, ActivityRequest( name, description, start_time, end_time, type, cost, location_id))
    }
}