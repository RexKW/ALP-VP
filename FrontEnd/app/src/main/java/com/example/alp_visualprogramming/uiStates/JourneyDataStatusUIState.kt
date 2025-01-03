package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ItineraryDestinationModel

interface JourneyDataStatusUIState {
    object Start : JourneyDataStatusUIState
    object Loading : JourneyDataStatusUIState
    data class Success(val data: List<ItineraryDestinationModel>) : JourneyDataStatusUIState
    data class Failed(val errorMessage: String?) : JourneyDataStatusUIState

}