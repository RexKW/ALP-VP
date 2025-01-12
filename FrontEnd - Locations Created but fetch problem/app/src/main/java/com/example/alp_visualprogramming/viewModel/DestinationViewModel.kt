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
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.repository.DestinationRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.DestinationDataStatusUIState
import com.example.alp_visualprogramming.uiStates.DestinationUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DestinationViewModel(
    private val userRepository: UserRepository,
    private val destinationRepository: DestinationRepository,
) : ViewModel() {
    private val _destinationUIState = MutableStateFlow(DestinationUIState())
    var dataStatus: DestinationDataStatusUIState by mutableStateOf(DestinationDataStatusUIState.Start)
        private set

    var currItineraryId by mutableStateOf<Int?>(null)

    fun setItineraryId(itineraryId: Int){
        currItineraryId = itineraryId
    }

    fun initializeDestinationItineraryId(itineraryId: Int, token: String, navController: NavController){
        setItineraryId(itineraryId)
        navController.navigate("Destinations")
    }

    fun searchDestination(){

    }

    fun getAllDestinations(token: String){
        viewModelScope.launch {
            dataStatus = DestinationDataStatusUIState.Loading

            try {
                val call = destinationRepository.getAllDestinations(token)
                call.enqueue(object : Callback<GetAllDestinationResponse> {
                    override fun onResponse(
                        call: Call<GetAllDestinationResponse>,
                        res: Response<GetAllDestinationResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = DestinationDataStatusUIState.Success(res.body()!!.data)

                            Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = DestinationDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllDestinationResponse>, t: Throwable) {
                        dataStatus = DestinationDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = DestinationDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val destinationRepository = application.container.destinationRepository
                DestinationViewModel(userRepository, destinationRepository)
            }
        }
    }
}
