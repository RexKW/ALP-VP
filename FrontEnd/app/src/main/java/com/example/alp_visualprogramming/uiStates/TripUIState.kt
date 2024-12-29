package com.example.alp_visualprogramming.uiStates

import com.example.alp_visualprogramming.models.ItineraryModel

data class TripUIState (
    var trips: List<ItineraryModel> = emptyList(),
    var searchInput: MutableList<String> = mutableListOf(),
)
