package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.DestinationModel

sealed interface DestinationDataStatusUIState {
    data class Success(val data: List<DestinationModel>): DestinationDataStatusUIState
    object Start: DestinationDataStatusUIState
    object Loading: DestinationDataStatusUIState
    data class Failed(val errorMessage:String): DestinationDataStatusUIState
}