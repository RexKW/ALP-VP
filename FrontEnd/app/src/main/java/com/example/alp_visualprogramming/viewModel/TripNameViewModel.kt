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
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository

class TripNameViewModel(
    itineraryRepository: ItineraryRepository,
    userRepository: UserRepository
): ViewModel() {
    var titleInput by mutableStateOf("")
     private set

    var isCreate by mutableStateOf(false)
    private set

    fun changeTitleInput(title: String) {
        titleInput = title
    }

    fun checkNullFormValues(){
        if(titleInput.isNotEmpty()){
            isCreate = true

        }else{
            isCreate = false
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val itineraryRepository = application.container.itineraryRepository
                val userRepository = application.container.userRepository
                TripNameViewModel(itineraryRepository, userRepository)
            }
        }
    }


}
