package com.example.alp_visualprogramming.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.alp_visualprogramming.ItineraryApplication
import com.example.alp_visualprogramming.models.ActivityModel
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.DestinationDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
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

    fun getLocationName(){
//        viewModelScope.launch {
//            dataStatus = DestinationDataStatusUIState.Loading
//
//            try {
//                val call = destinationRepository.getAllDestinations(token)
//                call.enqueue(object : Callback<GetAllDestinationResponse> {
//                    override fun onResponse(
//                        call: Call<GetAllDestinationResponse>,
//                        res: Response<GetAllDestinationResponse>
//                    ) {
//                        if (res.isSuccessful) {
//                            dataStatus = DestinationDataStatusUIState.Success(res.body()!!.data)
//
//                            Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
//                        } else {
//                            val errorMessage = Gson().fromJson(
//                                res.errorBody()!!.charStream(),
//                                ErrorModel::class.java
//                            )
//
//                            dataStatus = DestinationDataStatusUIState.Failed(errorMessage.errors)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<GetAllDestinationResponse>, t: Throwable) {
//                        dataStatus = DestinationDataStatusUIState.Failed(t.localizedMessage)
//                    }
//
//                })
//            } catch (error: IOException) {
//                dataStatus = DestinationDataStatusUIState.Failed(error.localizedMessage)
//            }
//        }
    }


}