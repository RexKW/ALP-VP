package com.example.alp_visualprogramming.viewModel

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
import com.example.alp_visualprogramming.models.ExploreItineraryResponse
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.ExploreDataStatusUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ExploreViewModel(
    private val userRepository: UserRepository,
    private val itineraryRepository: ItineraryRepository,

): ViewModel() {
    var dataStatus: ExploreDataStatusUIState by mutableStateOf(ExploreDataStatusUIState.Start)
        private set

    fun getAllItineraries(token: String) {
        dataStatus = ExploreDataStatusUIState.Loading
        viewModelScope.launch{
            try {
                val call = itineraryRepository.exploreItineraries(token)
                call.enqueue(object : Callback<ExploreItineraryResponse> {
                    override fun onResponse(
                        call: Call<ExploreItineraryResponse>,
                        res: Response<ExploreItineraryResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = ExploreDataStatusUIState.Success(res.body()!!.data)

                        }
                    }

                    override fun onFailure(call: Call<ExploreItineraryResponse>, t: Throwable) {
                        dataStatus = ExploreDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            }catch (error: IOException){
                dataStatus = ExploreDataStatusUIState.Failed(error.localizedMessage)

            }

        }
    }

    fun clearDataErrorMessage() {
        dataStatus = ExploreDataStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val itineraryRepository = application.container.itineraryRepository
                ExploreViewModel(userRepository, itineraryRepository)
            }
        }
    }

}