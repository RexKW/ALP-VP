package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.DestinationModel

interface DestinationDBDataStatusUIState {
    data class Success(val data: DestinationModel): DestinationDBDataStatusUIState
    object Start: DestinationDBDataStatusUIState
    object Loading: DestinationDBDataStatusUIState
    data class Failed(val errorMessage:String): DestinationDBDataStatusUIState

}