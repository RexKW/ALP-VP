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
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.InvitedTripDataStatusUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InvitedTripsViewModel(
    private val userRepository: UserRepository,
    private val itineraryRepository: ItineraryRepository,

):ViewModel() {
    var dataStatus: InvitedTripDataStatusUIState by mutableStateOf( InvitedTripDataStatusUIState.Start)
        private set
    fun getAllInvitedItineraries(token: String) {
        viewModelScope.launch {
            Log.d("token-home", "TOKEN AT HOME: ${token}")

            dataStatus =  InvitedTripDataStatusUIState.Loading

            try {
                val call = itineraryRepository.getAllInvitedItineraries(token)
                call.enqueue(object : Callback<GetAllItineraryResponse> {
                    override fun onResponse(
                        call: Call<GetAllItineraryResponse>,
                        res: Response<GetAllItineraryResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus =  InvitedTripDataStatusUIState.Success(res.body()!!.data)

                            Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus =  InvitedTripDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllItineraryResponse>, t: Throwable) {
                        dataStatus =  InvitedTripDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus =  InvitedTripDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val itineraryRepository = application.container.itineraryRepository
                InvitedTripsViewModel(userRepository, itineraryRepository)
            }
        }
    }

    fun clearDataErrorMessage() {
        dataStatus =  InvitedTripDataStatusUIState.Start
    }

    fun formatDate(dateString: String?): String {
        Log.d("DateFormat", "Received date string: $dateString")
        if (dateString.isNullOrEmpty()) {
            return "Invalid Date"
        }

        return try {
            val defaultDate = dateString ?: "No Date"
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = format.parse(defaultDate)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}