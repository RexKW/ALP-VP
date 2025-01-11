package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ItineraryModel

sealed interface InvitedTripDataStatusUIState {
    data class Success(val data: List<ItineraryModel>) : InvitedTripDataStatusUIState
    data class Failed(val errorMessage: String) : InvitedTripDataStatusUIState
    object Loading : InvitedTripDataStatusUIState
    object Start : InvitedTripDataStatusUIState

}