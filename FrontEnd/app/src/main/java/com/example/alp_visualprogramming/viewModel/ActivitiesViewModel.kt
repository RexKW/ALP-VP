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
import com.example.alp_visualprogramming.models.DayModel
import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GetAllActivityResponse
import com.example.alp_visualprogramming.models.GetAllDayResponse
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.ActivityDataStatusUIState
import com.example.alp_visualprogramming.uiStates.DayDataStatusUIState
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivitiesViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
): ViewModel() {
    var dataStatus: ActivityDataStatusUIState by mutableStateOf(ActivityDataStatusUIState.Start)
        private set

    var dayDataStatus: DayDataStatusUIState by mutableStateOf(DayDataStatusUIState.Start)
        private set

    var selectedDay by mutableStateOf<Int?>(null)

    var currItineraryId by mutableStateOf<Int?>(null)

    fun changeCurrItineraryId(itineraryId: Int){
        currItineraryId = itineraryId
    }

    fun updateSelectedDay(dayId: Int){
        selectedDay = dayId
    }


    fun getAllDaysInitial(itineraryDestinationId: Int, token: String, navController: NavController, itineraryId: Int){
        viewModelScope.launch {
            changeCurrItineraryId(itineraryId)
            dayDataStatus = DayDataStatusUIState.Loading
            try {
                val call =  activityRepository.getAllDays(token, itineraryDestinationId)
                call.enqueue(object : Callback<GetAllDayResponse> {
                    override fun onResponse(
                        call: Call<GetAllDayResponse>,
                        res: Response<GetAllDayResponse>
                    ) {
                        if (res.isSuccessful) {
                            dayDataStatus = DayDataStatusUIState.Success(res.body()!!.data)
                            getAllActivities(res.body()!!.data[0].id, token, navController)


                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dayDataStatus = DayDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllDayResponse>, t: Throwable) {
                        dayDataStatus = DayDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dayDataStatus = DayDataStatusUIState.Failed(error.localizedMessage)
            }

        }
    }

    fun getAllDays(itineraryDestinationId: Int, token: String, navController: NavController){
        viewModelScope.launch {
            dayDataStatus = DayDataStatusUIState.Loading
            try {
                val call =  activityRepository.getAllDays(token, itineraryDestinationId)
                call.enqueue(object : Callback<GetAllDayResponse> {
                    override fun onResponse(
                        call: Call<GetAllDayResponse>,
                        res: Response<GetAllDayResponse>
                    ) {
                        if (res.isSuccessful) {
                            dayDataStatus = DayDataStatusUIState.Success(res.body()!!.data)


                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dayDataStatus = DayDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllDayResponse>, t: Throwable) {
                        dayDataStatus = DayDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dayDataStatus = DayDataStatusUIState.Failed(error.localizedMessage)
            }

        }
    }


    fun getAllActivities(dayId: Int, token: String, navController: NavController){
        viewModelScope.launch {
            updateSelectedDay(dayId)
            dataStatus = ActivityDataStatusUIState.Loading
            try {
                val call =  activityRepository.getAllActivities(token, dayId)
                call.enqueue(object : Callback<GetAllActivityResponse> {
                    override fun onResponse(
                        call: Call<GetAllActivityResponse>,
                        res: Response<GetAllActivityResponse>
                    ) {
                        if (res.isSuccessful) {

                            dataStatus = ActivityDataStatusUIState.Success(res.body()!!.data)
                            navController.navigate("Activities/$dayId")

                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = ActivityDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllActivityResponse>, t: Throwable) {
                        dataStatus = ActivityDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = ActivityDataStatusUIState.Failed(error.localizedMessage)
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val activityRepository = application.container.activityRepository
                ActivitiesViewModel(userRepository, activityRepository)
            }
        }
    }

    fun formatDate(date: Date, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }

    fun clearDataDayErrorMessage() {
        dayDataStatus = DayDataStatusUIState.Start
    }

    fun clearDataErrorMessage() {
        dataStatus = ActivityDataStatusUIState.Start
    }


}