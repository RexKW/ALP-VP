package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ExploreItineraryModel

interface ExploreDataStatusUIState {
    data object Start : ExploreDataStatusUIState
    data object Loading : ExploreDataStatusUIState
    data class Success(val data: List<ExploreItineraryModel>) : ExploreDataStatusUIState
    data class Failed(val errorMessage: String) : ExploreDataStatusUIState

}