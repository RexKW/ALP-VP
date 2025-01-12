package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.ActivityRequest
import com.example.alp_visualprogramming.models.ActualBudgetResponse
import com.example.alp_visualprogramming.models.BudgetRequest
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetActivityResponse
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import com.example.alp_visualprogramming.models.GetAllPlannedBudgetResponse
import com.example.alp_visualprogramming.models.UpdateActivityRequest
import com.example.alp_visualprogramming.service.ActivityAPIService
import com.example.alp_visualprogramming.service.BudgetAPIService
import retrofit2.Call

interface BudgetRepository {
    fun getPlannedBudget(token: String, itineraryId: Int): Call<GetAllPlannedBudgetResponse>
    fun getActualBudget(token: String, itineraryId: Int): Call<ActualBudgetResponse>
    fun createBudget(token: String, itineraryId: Int, accommodation:Double, transport:Double, shoppingEntertainment:Double, culinary:Double, healthcare:Double, sightSeeing:Double, sport:Double ): Call<GeneralResponseModel>
    fun updateBudget(token: String, id: Int, accommodation:Double, transport:Double, shoppingEntertainment:Double, culinary:Double, healthcare:Double, sightSeeing:Double, sport:Double ): Call<GeneralResponseModel>
}

class NetworkBudgetRepository(
    private val budgetAPIService: BudgetAPIService
): BudgetRepository {

    override fun getPlannedBudget(token: String, itineraryId: Int): Call<GetAllPlannedBudgetResponse> {
        return budgetAPIService.getPlannedBudget(token, itineraryId)

    }

    override fun getActualBudget(token: String, itineraryId: Int): Call<ActualBudgetResponse> {
        return budgetAPIService.getActualBudget(token, itineraryId)
    }

    override fun createBudget(token: String, itineraryId: Int, accommodation:Double, transport:Double, shoppingEntertainment:Double, culinary:Double, healthcare:Double, sightSeeing:Double, sport:Double): Call<GeneralResponseModel> {
        return budgetAPIService.createBudget(token, itineraryId, BudgetRequest(accommodation, transport, shoppingEntertainment, culinary, healthcare, sightSeeing, sport))
    }

    override fun updateBudget(token: String, id: Int, accommodation:Double, transport:Double, shoppingEntertainment:Double, culinary:Double, healthcare:Double, sightSeeing:Double, sport:Double): Call<GeneralResponseModel> {
        return budgetAPIService.updateBudget(token, id, BudgetRequest(accommodation, transport, shoppingEntertainment, culinary, healthcare, sightSeeing, sport))
    }
}
