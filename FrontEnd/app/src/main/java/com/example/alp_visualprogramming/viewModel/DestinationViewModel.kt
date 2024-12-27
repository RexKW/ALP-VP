package com.example.alp_visualprogramming.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.repository.DestinationRepositories
import com.example.alp_visualprogramming.view.uiState.DestinationDataStatusUIState
import com.example.alp_visualprogramming.view.uiState.DestinationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DestinationViewModel(private val destinationDBRepositories: DestinationRepositories) : ViewModel() {
    private val _destinationUIState = MutableStateFlow(DestinationUIState())
    var dataStatus: DestinationDataStatusUIState by mutableStateOf(DestinationDataStatusUIState.Start)
        private set


    fun getAllDestinations(){
        viewModelScope.launch {
            dataStatus = DestinationDataStatusUIState.Loading

            try {
                val destinations = destinationDBRepositories.getAllDestinations()
                dataStatus = DestinationDataStatusUIState.Success(destinations)
            }catch (e: Exception){
                dataStatus = DestinationDataStatusUIState.Failed("Failed to fetch destinations")
            }
        }
    }
}
