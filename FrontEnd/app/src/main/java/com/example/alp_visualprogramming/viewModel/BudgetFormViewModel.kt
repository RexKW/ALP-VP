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
import androidx.navigation.NavController
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.models.ActivityModel
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllPlannedBudgetResponse
import com.example.alp_visualprogramming.repository.BudgetRepository
import com.example.alp_visualprogramming.uiStates.BudgetDataStatusUIState
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BudgetFormViewModel(
    private val budgetRepository: BudgetRepository
): ViewModel() {
    var dataStatus: BudgetDataStatusUIState by mutableStateOf(BudgetDataStatusUIState.Start)
        private set

    var accommodation by mutableStateOf(0.0)
        private set
    fun changeAccommodationInput(newValue: Double) {
        accommodation = newValue
    }
    var transport by mutableStateOf(0.0)
        private set
    fun changeTransportInput(newValue: Double) {
        transport = newValue
    }

    var isUpdate by mutableStateOf(false)
        private set
    fun changeIsUpdate(newValue: Boolean) {
        isUpdate = newValue
    }

    var currItineraryid by mutableStateOf(0)
        private set
    fun changeCurrItineraryId(newValue: Int) {
        currItineraryid = newValue
    }



    var entertainmentShopping by mutableStateOf(0.0)
        private set
    fun changeEntertainmentShoppingInput(newValue: Double) {
        entertainmentShopping = newValue
    }
    var culinary by mutableStateOf(0.0)
        private set
    fun changeCulinaryInput(newValue: Double) {
        culinary = newValue
    }
    var sightSeeing by mutableStateOf(0.0)
        private set
    fun changeSightSeeingInput(newValue: Double) {
        sightSeeing = newValue
    }
    var healthcare by mutableStateOf(0.0)
        private set
    fun changeHealthcareInput(newValue: Double) {
        healthcare = newValue
    }
    var sport by mutableStateOf(0.0)
        private set
    fun changeSportInput(newValue: Double) {
        sport = newValue
    }

    fun checkNullFormValues(){

    }



    fun createBudget(navController: NavController, token:String, itineraryId: Int){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
                Log.d("json", "Before: $currItineraryid")
                val call = budgetRepository.createBudget(token = token, accommodation = accommodation, transport = transport, shoppingEntertainment = entertainmentShopping, culinary = culinary, sightSeeing = sightSeeing, healthcare = healthcare, sport = sport, itineraryId = itineraryId)
                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("API Response", "Success: ${res.body()}")
                            resetViewModel()
                            navController.navigate("Journey/$itineraryId") {
                                popUpTo("Create") { inclusive = true }
                            }
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.e("API Response", "Error: ${res.errorBody()}")
                            submissionStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("API Response", "Failure: ${t.message}")
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            }catch (e: IOException){

            }
        }
    }

    fun initializeUpdate(token: String, itineraryId: Int){
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




                                Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
                            }
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

    fun updateBudget( navController: NavController, token: String){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading

            try{
                val call = budgetRepository.updateBudget(token = token, id = currItineraryid, accommodation = accommodation, transport = transport, shoppingEntertainment = entertainmentShopping, culinary = culinary, sightSeeing = sightSeeing, healthcare = healthcare, sport = sport)
                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            resetViewModel()
                        }

                    }
                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("API Response", "Failure: ${t.message}")
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }


                })
            }catch(e: IOException){

            }


        }
    }


    var isCreate by mutableStateOf(true)
        private set

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)


    fun resetViewModel(){
        changeAccommodationInput(0.0)
        changeTransportInput(0.0)
        changeEntertainmentShoppingInput(0.0)
        changeCulinaryInput(0.0)
        changeSightSeeingInput(0.0)
        changeHealthcareInput(0.0)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val budgetRepository = application.container.budgetRepository
                BudgetFormViewModel(budgetRepository)
            }
        }
    }



    fun clearDataErrorMessage() {
        submissionStatus = StringDataStatusUIState.Start
    }
}