package com.example.alp_visualprogramming.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState

class ActivityFormViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    var titleInput by mutableStateOf("")
        private set

    fun changeTitleInput(title: String) {
        titleInput = title
    }

    var startTimeInput by mutableStateOf("")
        private set

    var endTimeInput by mutableStateOf("")
        private set

    var typeInput by mutableStateOf("")
        private set

    fun changeTypeInput(type: String) {
        typeInput = type
    }

    var costInput by mutableStateOf<Double?>(null)



    var descriptionInput by mutableStateOf("")

    fun changeDescriptionInput(description: String) {
        descriptionInput = description
    }



    var isCreate by mutableStateOf(false)
        private set

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val activityRepository = application.container.activityRepository
                ActivityFormViewModel(userRepository, activityRepository)
            }
        }
    }
}