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
import com.example.alp_visualprogramming.repository.DestinationRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.DestinationDataStatusUIState
import com.example.alp_visualprogramming.uiStates.DestinationUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DestinationViewModel(
    private val userRepository: UserRepository,
    private val destinationRepository: DestinationRepository,
) : ViewModel() {
    private val _destinationUIState = MutableStateFlow(DestinationUIState())
    var dataStatus: DestinationDataStatusUIState by mutableStateOf(DestinationDataStatusUIState.Start)
        private set

    var currItineraryId by mutableStateOf<Int?>(null)

    fun setItineraryId(itineraryId: Int) {
        currItineraryId = itineraryId
    }

    fun initializeDestinationItineraryId(itineraryId: Int, token: String, navController: NavController) {
        setItineraryId(itineraryId)
        navController.navigate("Destinations")
    }

    fun searchDestination() {
        // Implementasi pencarian destinasi jika diperlukan
    }

    fun getAllDestinations(token: String) {
        viewModelScope.launch {
            dataStatus = DestinationDataStatusUIState.Loading
            try {
                // Panggil API secara langsung menggunakan `suspend` function
                val response = destinationRepository.getAllDestinations(token)
                dataStatus = DestinationDataStatusUIState.Success(response)
                Log.d("data-result", "TODO LIST DATA: $dataStatus")
            } catch (e: HttpException) {
                // Tangani kesalahan dari respons HTTP
                val errorMessage = try {
                    Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorModel::class.java)
                } catch (jsonError: Exception) {
                    ErrorModel(errors = listOf("Unknown error occurred").toString())
                }
                dataStatus = DestinationDataStatusUIState.Failed(errorMessage.errors)
            } catch (e: IOException) {
                // Tangani kesalahan jaringan
                dataStatus = DestinationDataStatusUIState.Failed(e.localizedMessage ?: "Network error")
            } catch (e: Exception) {
                // Tangani kesalahan umum lainnya
                dataStatus = DestinationDataStatusUIState.Failed(e.localizedMessage ?: "An unexpected error occurred")
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
