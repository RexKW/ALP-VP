package com.example.alp_visualprogramming.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.models.ActivityModel
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityDetailViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
): ViewModel() {
    var currActivity by mutableStateOf<ActivityModel?>(null)

    fun updateActivity(activity: ActivityModel){
        currActivity = activity
    }

    fun getActivity(activity: ActivityModel, token: String, navController: NavController){
        updateActivity(activity)
        navController.navigate("ActivityDetail")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val activityRepository = application.container.activityRepository
                ActivityDetailViewModel(userRepository, activityRepository)
            }
        }
    }

    fun parseDate(dateString: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun formatDate(date: Date, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }


}