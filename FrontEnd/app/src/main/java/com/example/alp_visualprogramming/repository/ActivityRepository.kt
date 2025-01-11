package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.ActivityRequest
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetActivityResponse
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import com.example.alp_visualprogramming.models.UpdateActivityRequest
import com.example.alp_visualprogramming.service.ActivityAPIService
import retrofit2.Call

interface ActivityRepository {
    fun getAllActivities(token: String, dayId: Int): Call<GetAllActivityResponse>
    fun getActivity(token: String, activityId: Int): Call<GetActivityResponse>
    fun createActivity(token: String, dayId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int): Call<GeneralResponseModel>
    fun getAllDays(token: String, itinerary_destination_Id: Int): Call<GetAllDayResponse>
    fun deleteActivity(token: String, activityId: Int): Call<GeneralResponseModel>
    fun updateActivity(token: String, activityId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int, dayId: Int): Call<GeneralResponseModel>
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

    override fun createActivity(token: String, dayId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int): Call<GeneralResponseModel> {
        return activityAPIService.createActivity(token, dayId, ActivityRequest( name, description, start_time, end_time, type, cost, location_id))
    }

    override fun deleteActivity(token: String, activityId: Int): Call<GeneralResponseModel> {
        return activityAPIService.deleteActivity(token, activityId)
    }

    override fun updateActivity(token: String, activityId: Int, name: String, description: String, start_time: String, end_time: String, type: String, cost: Double, location_id: Int, dayId: Int): Call<GeneralResponseModel> {
        return activityAPIService.updateActivity(token, activityId, UpdateActivityRequest(dayId, name, description, start_time, end_time, type, cost, location_id))

    }
}