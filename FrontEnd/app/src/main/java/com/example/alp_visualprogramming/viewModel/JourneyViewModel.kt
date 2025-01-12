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
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.repository.ItineraryDestinationRepository
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.uiStates.JourneyDataStatusUIState
import com.example.alp_visualprogramming.uiStates.JourneyUIState
import com.example.alp_visualprogramming.uiStates.TripDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class JourneyViewModel(
    private val userRepository: UserRepository,
    private val itineraryRepository: ItineraryRepository,
    private val itineraryDestinationRepository: ItineraryDestinationRepository,
): ViewModel() {

    var dataStatus: JourneyDataStatusUIState by mutableStateOf(JourneyDataStatusUIState.Start)
        private set

    var canEdit by mutableStateOf(false)

    var owner by mutableStateOf(false)

    fun changeOwner(value: Boolean){
        owner = value
    }

    fun changeCanEdit(value: Boolean){
        canEdit= value
    }

    var dataStatusDest: JourneyDataStatusUIState by mutableStateOf(JourneyDataStatusUIState.Start)

    fun getJourney(itineraryId:Int, navController: NavController){
        navController.navigate("Journey/$itineraryId")
    }

    fun viewJourney(itinerary_Id: Int, navController: NavController){
        changeCanEdit(false)
        navController.navigate("Explore/$itinerary_Id")
    }

    fun getAllJourneys(token:String, itinerary_Id:Int, explore:Boolean){
        viewModelScope.launch {
            dataStatus = JourneyDataStatusUIState.Loading
            try {
                val call =  itineraryDestinationRepository.getAllItineraryDestinations(token, itinerary_Id)
                call.enqueue(object : Callback<GetAllItineraryDestinationResponse> {
                    override fun onResponse(
                        call: Call<GetAllItineraryDestinationResponse>,
                        res: Response<GetAllItineraryDestinationResponse>
                    ) {
                        if (res.isSuccessful) {
                            val itineraryData = res.body()!!.data
                            viewModelScope.launch {
                                val deferredNames = itineraryData.map { itinerary ->
                                    async {
                                        val name = getDestinationName(token, itinerary.destination_id)
                                        itinerary.name = name
                                    }
                                }
                                deferredNames.awaitAll()
                                viewModelScope.launch {

                                        val role = getUserRole(token, itinerary_Id)
                                        if(role != "member"){
                                            if(!explore){
                                                changeCanEdit(true)
                                            }
                                            if(role == "owner"){
                                                changeOwner(true)
                                            }

                                        }

                                    dataStatus = JourneyDataStatusUIState.Success(itineraryData)

                                }



                                Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = JourneyDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllItineraryDestinationResponse>, t: Throwable) {
                        dataStatus = JourneyDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = JourneyDataStatusUIState.Failed(error.localizedMessage)
            }

        }
    }

    fun deleteJourney(token:String, itinerary_Id:Int, navController: NavController){
        viewModelScope.launch {
            dataStatus = JourneyDataStatusUIState.Loading
            try {
                val call = itineraryRepository.deleteItinerary( token, itinerary_Id)
                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            navController.navigate("Home")

                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        dataStatus = JourneyDataStatusUIState.Failed(t.localizedMessage)
                    }

                })


            } catch (error: IOException) {

            }
        }
    }

    suspend fun getDestinationName(token:String, destinationId: Int): String{
        return suspendCancellableCoroutine { continuation ->
            val call = itineraryDestinationRepository.getDestinationById(destinationId, token)

            call.enqueue(object : Callback<GetDestinationResponse> {
                override fun onResponse(
                    call: Call<GetDestinationResponse>,
                    res: Response<GetDestinationResponse>
                ) {
                    if (res.isSuccessful) {
                        val destinationData = res.body()?.data
                        Log.d("DestinationResponse", "Response: $destinationData")
                        continuation.resume(destinationData?.name ?: "Unknown")
                    } else {
                        continuation.resume("Unknown")
                    }
                }

                override fun onFailure(call: Call<GetDestinationResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }

    }

    suspend fun getUserRole(token:String, itineraryId: Int): String{
        return suspendCancellableCoroutine { continuation ->
            val call = userRepository.getUserRole(token = token, id = itineraryId)
            call.enqueue(object: Callback<UserRoleResponse> {
                override fun onResponse(
                    call: Call<UserRoleResponse>,
                    res: Response<UserRoleResponse>
                ) {
                    if (res.isSuccessful) {
                        val userRoleData = res.body()?.data
                        continuation.resume(userRoleData ?: "Unknown")
                    }

                }

                override fun onFailure(call: Call<UserRoleResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    fun formatDate(dateString: String?): String {
        return try {
            val defaultDate = dateString ?: "No Date"
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = format.parse(defaultDate)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

    fun clearDataErrorMessage() {
        dataStatus = JourneyDataStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val itineraryRepository = application.container.itineraryRepository
                val itineraryDestinationRepository = application.container.itineraryDestinationRepository
                JourneyViewModel(userRepository, itineraryRepository,itineraryDestinationRepository)
            }
        }
    }
}