package com.example.alp_visualprogramming.viewModel

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.collection.mutableFloatFloatMapOf
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
import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.models.GetItineraryDestinationResponse
import com.example.alp_visualprogramming.models.ItineraryModel
import com.example.alp_visualprogramming.repository.DestinationRepository
import com.example.alp_visualprogramming.repository.ItineraryDestinationRepository
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class JourneyFormViewModel(
    private val userRepository: UserRepository,
    private val itineraryDestinationRepository: ItineraryDestinationRepository,
    private val destinationRepository: DestinationRepository,

    ): ViewModel() {
    var startDateInput by mutableStateOf("")
        private set

    var endDateInput by mutableStateOf("")
        private set

    var locationInput by mutableStateOf<DestinationModel?>(null)
    private set

    var isCreate by mutableStateOf(false)
        private set

    var isUpdate by mutableStateOf(false)
        private set

    var currItinerarDestinationId by mutableStateOf<Int?>(null)

    fun changeCurrItinerarDestinationId(itineraryDestinationId: Int){
        currItinerarDestinationId = itineraryDestinationId
    }

    fun changeIsUpdate(update: Boolean){
        isUpdate = update
    }

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set



    var currItineraryId by mutableStateOf<Int?>(null)


    fun changeStartDateInput(startDate: String) {
        startDateInput = startDate
    }

    fun changeEndDateInput(endDate: String) {
        endDateInput = endDate
    }

    fun changeLocationInput(location: DestinationModel) {
        locationInput = location
    }

    fun changeCurrItineraryId(itineraryId: Int){
        currItineraryId = itineraryId
    }

    fun checkNullFormValues(){
        if(startDateInput.isNotEmpty() && endDateInput.isNotEmpty() && locationInput != null){
            isCreate = true

        }else{
            isCreate = false
        }
    }

    fun initializeFormDestination(navController: NavController, token: String, itineraryId: Int, itinerary_destination_id: Int?, update:Boolean){
        changeCurrItineraryId(itineraryId)
        Log.d("JourneyFormViewModel", "Itinerary ID: $itineraryId")
        if(update && itinerary_destination_id != null){
            changeCurrItinerarDestinationId(itinerary_destination_id)
            changeIsUpdate(true)
            initializeUpdateJourney(token,itinerary_destination_id , navController)
        }
        navController.navigate("FormDestination")
    }

    fun initializeUpdateJourney(token: String, itinerary_destination_id: Int, navController: NavController){
        viewModelScope.launch {
            val call = itineraryDestinationRepository.getItineraryDestination(itinerary_destination_id, token)
            call.enqueue(object: Callback<GetItineraryDestinationResponse>{
                override fun onResponse(
                    call: Call<GetItineraryDestinationResponse>,
                    res: Response<GetItineraryDestinationResponse>
                ) {
                    if (res.isSuccessful) {
                        changeStartDateInput(res.body()?.data?.start_date ?: "")
                        changeEndDateInput(res.body()?.data?.start_date ?: "")
                        viewModelScope.launch{
                             getDestination(token, res.body()?.data?.destination_id?: 0)


                        }

                    }
                }


                override fun onFailure(call: Call<GetItineraryDestinationResponse>, t: Throwable) {

                }
            })
        }
    }

    suspend fun getDestination(token:String, destinationId: Int){
        return suspendCancellableCoroutine { continuation ->
            val call = destinationRepository.getDestinationById(token, destinationId)
            call.enqueue(object: Callback<GetDestinationResponse>{
                override fun onResponse(
                    call: Call<GetDestinationResponse>,
                    res: Response<GetDestinationResponse>
                ) {
                    if (res.isSuccessful) {
                        val destination = res.body()?.data
                        if (destination != null) {
                            changeLocationInput(destination)
                        }

                    }

                }

                override fun onFailure(call: Call<GetDestinationResponse>, t: Throwable) {

                }


            })


        }


    }

    fun selectDestination(destination: DestinationModel, navController: NavController){
        changeLocationInput(destination)
        navController.navigate("FormDestination")

    }

    fun initStartDatePickerDialog(context: Context): DatePickerDialog {
        // Initializing a Calendar
        val datePickerCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        val calYear = datePickerCalendar.get(Calendar.YEAR)
        val calMonth = datePickerCalendar.get(Calendar.MONTH)
        val calDay = datePickerCalendar.get(Calendar.DAY_OF_MONTH)

        datePickerCalendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, calYear: Int, calMonth: Int, calDay: Int ->
                startDateInput = "$calDay/${calMonth + 1}/$calYear"
                checkNullFormValues()
            }, calYear, calMonth, calDay
        )

        return datePickerDialog
    }

    fun showDatePickerDialog(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }

    fun initEndDatePickerDialog(context: Context): DatePickerDialog {
        // Initializing a Calendar
        val datePickerCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        val calYear = datePickerCalendar.get(Calendar.YEAR)
        val calMonth = datePickerCalendar.get(Calendar.MONTH)
        val calDay = datePickerCalendar.get(Calendar.DAY_OF_MONTH)

        datePickerCalendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, calYear: Int, calMonth: Int, calDay: Int ->
                endDateInput = "$calDay/${calMonth + 1}/$calYear"
                checkNullFormValues()
            }, calYear, calMonth, calDay
        )

        return datePickerDialog
    }

    fun deleteJourney(navController: NavController, token: String, journeyId: Int){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
                val call = itineraryDestinationRepository.deleteItineraryDestination(journeyId, token)

                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if(res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            navController.navigate("Journey/$currItineraryId")
                            resetViewModel()
                        }

                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("API Response", "Failure: ${t.message}")
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })


            }catch (e: IOException){
                submissionStatus = StringDataStatusUIState.Failed(e.localizedMessage)

            }
        }
    }




    fun createJourney(navController: NavController, token: String, locationId: Int){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
                val call = itineraryDestinationRepository.createItineraryDestination(token, startDateInput,  endDateInput, locationId =  locationId, itineraryId =  currItineraryId ?: 0, accomodationId = null)
                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("API Response", "Success: ${res.body()}")
                            navController.navigate("Journey/$currItineraryId") {
                                popUpTo("FormDestination") { inclusive = true }
                            }
                            resetViewModel()
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

    fun updateJourney(navController: NavController, token: String, locationId: Int, journeyId: Int){
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading
            try{
                val call = itineraryDestinationRepository.updateItineraryDestination(token, journeyId, startDateInput,  endDateInput, locationId =  locationId, accomodationId = null, itineraryId =  currItineraryId ?: 0)
                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: retrofit2.Call<GeneralResponseModel>,
                        res: retrofit2.Response<GeneralResponseModel>
                    ) {
                        if(res.isSuccessful){
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("API Response", "Success: ${res.body()}")
                            navController.navigate("Journey/$currItineraryId")
                            resetViewModel()
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


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val itineraryDestinationRepository = application.container.itineraryDestinationRepository
                val destinationRepository = application.container.destinationRepository
                JourneyFormViewModel(userRepository, itineraryDestinationRepository, destinationRepository)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertIsoToCustomFormat(isoString: String, customFormat: String = "yyyy-MM-dd HH:mm:ss"): String? {
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

    fun resetViewModel(){
        startDateInput = ""
        endDateInput = ""
        locationInput = null
        isCreate = false
        isUpdate = false
    }


}