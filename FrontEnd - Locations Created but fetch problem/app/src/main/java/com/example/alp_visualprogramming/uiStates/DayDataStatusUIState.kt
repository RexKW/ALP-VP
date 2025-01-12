package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ActivityModel
import com.example.alp_visualprogramming.models.DayModel

interface DayDataStatusUIState {
    data class Success(val data: List<DayModel>): DayDataStatusUIState
    object Start: DayDataStatusUIState
    object Loading: DayDataStatusUIState
    data class Failed(val errorMessage:String): DayDataStatusUIState
}