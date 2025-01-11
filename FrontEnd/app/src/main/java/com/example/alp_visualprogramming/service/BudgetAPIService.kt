package com.example.alp_visualprogramming.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BudgetAPIService {
    @GET("budgets/plannedBudget/{itineraryId}")
    fun getPlannedBudget(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int): Call<GetAllPlannedBudgetResponse>

    @GET("budgets/actualBudget/{itineraryId}")
    fun getActualBudget(@Header("X-API-TOKEN") token: String, @Path("itineraryId") itineraryId: Int): Call<GetAllActualBudgetResponse>

}