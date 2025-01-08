package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ActivityModel
import com.example.alp_visualprogramming.models.DestinationModel

sealed interface ActivityDataStatusUIState {
    data class Success(val data: List<ActivityModel>): ActivityDataStatusUIState
    object Start: ActivityDataStatusUIState
    object Loading: ActivityDataStatusUIState
    data class Failed(val errorMessage:String): ActivityDataStatusUIState
}