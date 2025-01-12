package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.BudgetModel
import com.example.alp_visualprogramming.models.DayModel

interface BudgetDataStatusUIState {
    data class Success(val data: List<BudgetModel>): BudgetDataStatusUIState
    object Start: BudgetDataStatusUIState
    object Loading: BudgetDataStatusUIState
    data class Failed(val errorMessage:String): BudgetDataStatusUIState
}