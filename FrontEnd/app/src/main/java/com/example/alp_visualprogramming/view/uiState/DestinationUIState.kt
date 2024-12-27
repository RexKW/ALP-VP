package com.example.alp_visualprogramming.view.uiState

import com.example.alp_visualprogramming.models.DestinationModel

data class DestinationUIState(
    var destinations: List<DestinationModel> = emptyList(),
    var searchInput: MutableList<String> = mutableListOf(),
)