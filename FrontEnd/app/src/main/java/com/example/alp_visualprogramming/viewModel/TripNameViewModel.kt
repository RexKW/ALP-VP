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
import androidx.navigation.NavHostController
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetCreatedItineraryResponse
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TripNameViewModel(
    private val itineraryRepository: ItineraryRepository,
    private val userRepository: UserRepository,
    private val budgetFormViewModel: BudgetFormViewModel
): ViewModel() {
    var titleInput by mutableStateOf("")
     private set

    var isCreate by mutableStateOf(false)
    private set

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    var currItineraryId by mutableStateOf(0)
    private set

    fun changeCurrItineraryId(newValue: Int) {
        currItineraryId = newValue
    }

    fun changeTitleInput(title: String) {
        titleInput = title
    }

    fun checkNullFormValues(){
        if(titleInput.isNotEmpty()){
            isCreate = true

        }else{
            isCreate = false
        }
    }

    fun createItinerary(navController: NavController, token: String) {
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading


            try {
                val call = itineraryRepository.createItinerary(token, titleInput)

                call.enqueue(object: Callback<GetCreatedItineraryResponse> {
                    override fun onResponse(
                        call: Call<GetCreatedItineraryResponse>,
                        res: Response<GetCreatedItineraryResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.toString())
                            val itineraryId = res.body()!!.data.id
                            changeCurrItineraryId(itineraryId)
                            navController.navigate("FormBudget/$itineraryId")
                            Log.d("itineraryId", "ITINERARY ID: $itineraryId")
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            submissionStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetCreatedItineraryResponse>, t: Throwable) {
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun clearDataErrorMessage() {
        submissionStatus = StringDataStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val itineraryRepository = application.container.itineraryRepository
                val userRepository = application.container.userRepository
                val budgetRepository = application.container.budgetRepository
                val budgetFormViewModel = BudgetFormViewModel(budgetRepository)
                TripNameViewModel(itineraryRepository, userRepository, budgetFormViewModel)
            }
        }
    }


}
