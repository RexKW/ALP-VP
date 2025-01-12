package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.ActualBudgetResponse
import com.example.alp_visualprogramming.models.BudgetRequest
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllPlannedBudgetResponse
import com.example.alp_visualprogramming.models.GetBudgetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BudgetAPIService {
    @GET("budget/plannedBudget/{itineraryId}")
    fun getPlannedBudget(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int): Call<GetAllPlannedBudgetResponse>

    @GET("budget/actualBudget/{itineraryId}")
    fun getActualBudget(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int): Call<ActualBudgetResponse>

    @POST("budget/create/{itineraryId}")
    fun createBudget(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int, @Body budgetRequest: BudgetRequest): Call<GeneralResponseModel>

    @PUT("budget/update/{id}")
    fun updateBudget(@Header("X-API-TOKEN") token: String, @Path("id") id: Int,  @Body budgetRequest: BudgetRequest): Call<GeneralResponseModel>



}
