package com.example.alp_visualprogramming.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.models.ActualBudgetResponse
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetAllPlannedBudgetResponse
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.repository.BudgetRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.BudgetDataStatusUIState
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class BudgetViewModel (
    private val budgetRepository: BudgetRepository,
    private val userRepository: UserRepository
): ViewModel(){
    var dataStatus: BudgetDataStatusUIState by mutableStateOf(BudgetDataStatusUIState.Start)
        private set

    var planned by mutableStateOf(0.0)
        private set

    fun changePlan(newValue: Double) {
        planned = newValue
    }

    var actual by mutableStateOf(0.0)
        private set
    fun changeActual(newValue: Double) {
        actual = newValue
    }
    var canEdit by mutableStateOf(false)

    var owner by mutableStateOf(false)

    fun changeOwner(value: Boolean){
        owner = value
    }

    fun changeCanEdit(value: Boolean){
        canEdit= value
    }

    fun getAllBudget(token: String, itineraryId: Int) {
        viewModelScope.launch {
            dataStatus = BudgetDataStatusUIState.Loading
            try {
                val call = budgetRepository.getPlannedBudget(token = token, itineraryId = itineraryId)
                call.enqueue(object : Callback<GetAllPlannedBudgetResponse> {
                    override fun onResponse(
                        call: Call<GetAllPlannedBudgetResponse>,
                        res: Response<GetAllPlannedBudgetResponse>
                    ) {
                        if (res.isSuccessful) {
                            val budgetData = res.body()?.data ?: emptyList()
                            viewModelScope.launch {
                                // Get user role
                                val role = getUserRole(token, itineraryId)
                                if (role != "member") {
                                    changeCanEdit(true)
                                }

                                // Calculate total planned budget
                                val totalBudget = budgetData.sumOf { it.estimated_budget }
                                changePlan(totalBudget)

                                // Get actual budget (secondary call)
                                try {





                                    val actualBudgetResponse = withContext(Dispatchers.IO) {
                                        budgetRepository.getActualBudget(token, itineraryId).execute()
                                    }

                                    if (actualBudgetResponse.isSuccessful) {
                                        val actualData = actualBudgetResponse.body()
                                        if (actualData != null) {
                                            // Calculate the total actual budget
                                            val totalActual = actualData.totalSport +
                                                    actualData.totalCulinary +
                                                    actualData.totalShoppingEntertainment +
                                                    actualData.totalTransport +
                                                    actualData.totalHealthcare +
                                                    actualData.totalAccommodation +
                                                    actualData.totalSightSeeing

                                            // Update the actual budget in the UI
                                            changeActual(totalActual)
                                            Log.d("budget", "Calculated Total Actual Budget: $totalActual")
                                        } else {
                                            Log.e("budget", "No data field in the response")
                                        }
                                    } else {
                                        val errorBody = actualBudgetResponse.errorBody()?.string()
                                        Log.e("budget", "Failed Response: $errorBody")
                                    }


                                } catch (e: Exception) {
                                    dataStatus = BudgetDataStatusUIState.Failed(e.localizedMessage ?: "Unknown error")
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            dataStatus = BudgetDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllPlannedBudgetResponse>, t: Throwable) {
                        dataStatus = BudgetDataStatusUIState.Failed(t.localizedMessage ?: "Unknown error")
                    }
                })
            } catch (error: IOException) {
                dataStatus = BudgetDataStatusUIState.Failed(error.localizedMessage ?: "Unknown error")
            }
        }
    }

    suspend fun getUserRole(token:String, itineraryId: Int): String{
        return suspendCancellableCoroutine { continuation ->
            val call = userRepository.getUserRole(token = token, id = itineraryId)
            call.enqueue(object: Callback<UserRoleResponse> {
                override fun onResponse(
                    call: Call<UserRoleResponse>,
                    res: Response<UserRoleResponse>
                ) {
                    if (res.isSuccessful) {
                        val userRoleData = res.body()?.data
                        continuation.resume(userRoleData ?: "Unknown")
                    }

                }

                override fun onFailure(call: Call<UserRoleResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val budgetRepository = application.container.budgetRepository
                BudgetViewModel(budgetRepository,userRepository)
            }
        }
    }


}