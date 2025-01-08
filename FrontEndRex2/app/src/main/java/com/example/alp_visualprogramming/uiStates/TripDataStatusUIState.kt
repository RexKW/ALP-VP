package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ItineraryModel

interface TripDataStatusUIState {
    data class Success(val data: List<ItineraryModel>): TripDataStatusUIState
    object Start: TripDataStatusUIState
    object Loading:  TripDataStatusUIState, DestinationDataStatusUIState
    data class Failed(val errorMessage:String): TripDataStatusUIState
}