package com.example.alp_visualprogramming.viewModel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
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
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.ActivityFormUIState
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class ActivityFormViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    private val _activityFormUIState = MutableStateFlow(ActivityFormUIState())
    val activityFormUIState: StateFlow<ActivityFormUIState>
        get() {
            return _activityFormUIState.asStateFlow()
        }


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

    fun changeTypeExpandedValue(
        expanded: Boolean
    ) {
        _activityFormUIState.update { currentState ->
            currentState.copy(
                typeDropdownExpandedValue = expanded
            )
        }
    }

    fun dismissStatusDropdownMenu() {
        _activityFormUIState.update { currentState ->
            currentState.copy(
                typeDropdownExpandedValue = false
            )
        }
    }


    var currDayId by mutableStateOf<Int>(0)
        private set

    var activityId by mutableStateOf<Int>(0)
        private set

    var locationId by mutableStateOf<Int>(0)
        private set

    var costInput by mutableStateOf<Double>(0.0)
    fun changeCostInput(cost: Double) {
        costInput = cost
    }



    var descriptionInput by mutableStateOf("")

    fun changeDescriptionInput(description: String) {
        descriptionInput = description
    }

    fun showTimePickerDialog(timePickerDialog: TimePickerDialog) {
        timePickerDialog.show()
    }

    fun initStartTimePickerDialog(context: Context): TimePickerDialog {
        // Initializing a Calendar
        val datePickerCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        val calHour = datePickerCalendar.get(Calendar.HOUR_OF_DAY)
        val calMinute = datePickerCalendar.get(Calendar.MINUTE)


        datePickerCalendar.time = Date()

        val timePickerDialog = TimePickerDialog(
            context,
            { _, calHour: Int, calMinute: Int ->
                val formattedHour = addLeadingZero(calHour)
                val formattedMinute = addLeadingZero(calMinute)
                startTimeInput = "$formattedHour:$formattedMinute"
            }, calHour, calMinute, true

        )

        return timePickerDialog
    }

    fun addLeadingZero(value: Int): String {
        return if (value < 10) "0$value" else value.toString()
    }

    fun initEndTimePickerDialog(context: Context): TimePickerDialog {
        // Initializing a Calendar
        val datePickerCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        val calHour = datePickerCalendar.get(Calendar.HOUR_OF_DAY)
        val calMinute = datePickerCalendar.get(Calendar.MINUTE)


        datePickerCalendar.time = Date()

        val timePickerDialog = TimePickerDialog(
            context,
            { _, calHour: Int, calMinute: Int ->
                val formattedHour = addLeadingZero(calHour)
                val formattedMinute = addLeadingZero(calMinute)
                endTimeInput = "$formattedHour:$formattedMinute"
                checkNullFormValues()
            }, calHour, calMinute, true

        )

        return timePickerDialog
    }





    var isCreate by mutableStateOf(false)
        private set

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)

    fun changeCurrDayId(dayId: Int){
        this.currDayId = dayId
    }

    fun changeActivityId(activityId: Int){
        this.activityId = activityId

    }

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

    fun initializeCreate(dayId: Int, navController: NavController){
        changeCurrDayId(dayId)
        navController.navigate("FormActivity")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeUpdate(dayId: Int, activity: ActivityModel, navController: NavController){
        changeCurrDayId(dayId)
        changeActivityId(activity.id)
        changeTitleInput(activity.name)
        changeTypeInput(activity.type)
        changeCostInput(activity.cost)
        changeDescriptionInput(activity.description)
        startTimeInput = convertIsoToCustomFormat(activity.start_time).toString()
        endTimeInput = convertIsoToCustomFormat(activity.end_time).toString()
        navController.navigate("FormActivity")
    }


    fun createActivity(navController: NavController, token: String){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
                val call = activityRepository.createActivity(token, name = titleInput, start_time = startTimeInput, end_time = endTimeInput, type = typeInput, cost = costInput, description = descriptionInput, location_id = 1, dayId = currDayId)
                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("API Response", "Success: ${res.body()}")
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.e("API Response", "Error: ${res.errorBody()}")
                            submissionStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("API Response", "Failure: ${t.message}")
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            }catch (e: IOException){

            }
        }
    }

    fun updateActivity( navController: NavController, token: String){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading

            try{
                val call = activityRepository.updateActivity(token = token, activityId = activityId, name = titleInput, start_time = startTimeInput, end_time = endTimeInput, type = typeInput, cost = costInput, description = descriptionInput, location_id = 1, dayId = currDayId)
                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)

                        }

                    }
                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("API Response", "Failure: ${t.message}")
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }


                })
            }catch(e: IOException){

            }


        }
    }

    fun deleteActivity( token: String){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
            val call = activityRepository.deleteActivity(token = token, activityId = activityId)
            call.enqueue(object: Callback<GeneralResponseModel>{
                override fun onResponse(
                    call: retrofit2.Call<GeneralResponseModel>,
                    res: retrofit2.Response<GeneralResponseModel>
                ) {

                }

                override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {

                }

            })

            }catch (e: IOException){

            }

        }
    }

    fun clearDataErrorMessage() {
        submissionStatus = StringDataStatusUIState.Start
    }

    fun checkNullFormValues(){
        if(titleInput != "" && startTimeInput != "" && endTimeInput != "" && typeInput != "" && descriptionInput != "" && costInput >= 0.0){

            if(startTimeInput>= endTimeInput){
                isCreate = false
            }else{
                isCreate = true
            }


        }else{
            isCreate = false
        }
    }

    fun resetViewModel(){
        titleInput = ""
        startTimeInput = ""
        endTimeInput = ""
        typeInput = ""
        descriptionInput = ""
        costInput = 0.0
        isCreate = false
        submissionStatus = StringDataStatusUIState.Start

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertIsoToCustomFormat(isoString: String, customFormat: String = "HH:mm"): String? {
        return try {
            val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
            val customFormatter = DateTimeFormatter.ofPattern(customFormat)
            val zonedDateTime = ZonedDateTime.parse(isoString, isoFormatter)
            zonedDateTime.format(customFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}