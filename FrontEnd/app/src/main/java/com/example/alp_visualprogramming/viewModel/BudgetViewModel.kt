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
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetAllPlannedBudgetResponse
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.repository.BudgetRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.BudgetDataStatusUIState
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
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
    var canEdit by mutableStateOf(false)

    var owner by mutableStateOf(false)

    fun changeOwner(value: Boolean){
        owner = value
    }

    fun changeCanEdit(value: Boolean){
        canEdit= value
    }

    fun getAllBudget(token: String, itineraryId: Int){
        viewModelScope.launch {
            dataStatus = BudgetDataStatusUIState.Loading
            try {
                val call =  budgetRepository.getPlannedBudget(token = token, itineraryId = itineraryId)
                call.enqueue(object : Callback<GetAllPlannedBudgetResponse> {
                    override fun onResponse(
                        call: Call<GetAllPlannedBudgetResponse>,
                        res: Response<GetAllPlannedBudgetResponse>
                    ) {
                        if (res.isSuccessful) {
                            val budgetData = res.body()!!.data

                                viewModelScope.launch {
                                    val role = getUserRole(token, itineraryId)
                                    if(role != "member"){
                                        changeCanEdit(true)
                                        if(role == "owner"){

                                        }
                                    }
                                    var totalBudget = 0.0
                                    for (budget in budgetData) {
                                        totalBudget += budget.estimatedBudget
                                    }
                                    changePlan(totalBudget)

                                }



                                Log.d("data-result", "TODO LIST DATA: ${dataStatus}")

                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = BudgetDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllPlannedBudgetResponse>, t: Throwable) {
                        dataStatus = BudgetDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = BudgetDataStatusUIState.Failed(error.localizedMessage)
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