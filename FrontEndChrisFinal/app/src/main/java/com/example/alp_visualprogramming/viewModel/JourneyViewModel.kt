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
import com.example.alp_visualprogramming.models.AccommodationIdWrapper
import com.example.alp_visualprogramming.models.AccommodationRequest
import com.example.alp_visualprogramming.models.AccommodationResponse
import com.example.alp_visualprogramming.models.AccommodationWrapper
import com.example.alp_visualprogramming.models.ErrorModel
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.repository.ItineraryDestinationRepository
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
    private val itineraryDestinationRepository: ItineraryDestinationRepository,
): ViewModel() {

    var dataStatus: JourneyDataStatusUIState by mutableStateOf(JourneyDataStatusUIState.Start)
        private set

    var canEdit: Boolean = false

    fun changeCanEdit(value: Boolean){
        canEdit= value
    }

    var dataStatusDest: JourneyDataStatusUIState by mutableStateOf(JourneyDataStatusUIState.Start)

    fun getJourney(itineraryId:Int, navController: NavController){
        navController.navigate("Journey/$itineraryId")
    }

    fun viewJourney(itinerary_Id: Int, navController: NavController){
        navController.navigate("Explore/$itinerary_Id")
    }

    fun getAllJourneys(token:String, itinerary_Id:Int){
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
                                            changeCanEdit(true)
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

    suspend fun getOrCreateAccommodation(
        token: String,
        accommodationRequest: AccommodationRequest
    ): AccommodationResponse {
        return suspendCancellableCoroutine { continuation ->
            val call = itineraryDestinationRepository.getOrCreateAccommodation(
                token = token,
                placeId = accommodationRequest.place_id,
                name = accommodationRequest.name,
                address = accommodationRequest.address,
                locationImage = accommodationRequest.location_image,
                placeApi = accommodationRequest.place_api,
                categories = accommodationRequest.categories.joinToString(","), // Convert to comma-separated
                cost = accommodationRequest.cost.toString(),
                people = accommodationRequest.people,
                openingHours = accommodationRequest.opening_hours,
                website = accommodationRequest.website,
                phone = accommodationRequest.phone
            )

            call.enqueue(object : Callback<AccommodationWrapper> { // Use AccommodationWrapper instead
                override fun onResponse(
                    call: Call<AccommodationWrapper>,
                    response: Response<AccommodationWrapper>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(responseBody.data) // `data` is the AccommodationResponse
                        } else {
                            continuation.resumeWithException(
                                IOException("Response body is null")
                            )
                        }
                    } else {
                        continuation.resumeWithException(
                            IOException("Failed to fetch/create accommodation: ${response.message()}")
                        )
                    }
                }

                override fun onFailure(call: Call<AccommodationWrapper>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun updateJourneyAccommodation(
        token: String,
        itineraryDestinationId: Int,
        accommodationRequest: AccommodationRequest
    ): AccommodationResponse {
        return suspendCancellableCoroutine { continuation ->
            val call = itineraryDestinationRepository.updateJourneyAccommodation(
                token = token,
                itineraryDestinationId = itineraryDestinationId,
                accommodationRequest = accommodationRequest
            )

            call.enqueue(object : Callback<AccommodationWrapper> {
                override fun onResponse(
                    call: Call<AccommodationWrapper>,
                    response: Response<AccommodationWrapper>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(responseBody.data)
                        } else {
                            continuation.resumeWithException(
                                IOException("Response body is null")
                            )
                        }
                    } else {
                        continuation.resumeWithException(
                            IOException("Failed to update journey accommodation: ${response.message()}")
                        )
                    }
                }

                override fun onFailure(call: Call<AccommodationWrapper>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    fun getAccommodationIdForJourney(token: String, journeyId: Int, onResult: (Int?) -> Unit) {
        viewModelScope.launch {
            try {
                // Call the repository method to check accommodation
                val response = itineraryDestinationRepository.checkAccommodation(token, journeyId)

                response.enqueue(object : Callback<AccommodationIdWrapper> {
                    override fun onResponse(
                        call: Call<AccommodationIdWrapper>,
                        res: Response<AccommodationIdWrapper>
                    ) {
                        if (res.isSuccessful) {
//                            val accommodationId = res.body()?.data?.id
                            val accommodationId = res.body()?.accommodation_id
                            onResult(accommodationId)
                        } else {
                            Log.e("JourneyViewModel", "Failed to fetch accommodation ID: ${res.message()}")
                            onResult(null)
                        }
                    }

                    override fun onFailure(call: Call<AccommodationIdWrapper>, t: Throwable) {
                        Log.e("JourneyViewModel", "Error fetching accommodation ID: ${t.message}")
                        onResult(null) // Handle the error gracefully
                    }
                })
            } catch (e: Exception) {
                Log.e("JourneyViewModel", "Error in getAccommodationIdForJourney: ${e.message}")
                onResult(null)
            }
        }
    }

    fun getAccommodationDetails(
        accommodationId: Int,
        onResult: (AccommodationResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = "7ffe2e10-558b-45ab-b3f6-fb4693051b5f" // Replace with dynamic token
                val response = itineraryDestinationRepository.getAccommodationDetails(token, accommodationId)

                response.enqueue(object : Callback<AccommodationWrapper> {
                    override fun onResponse(
                        call: Call<AccommodationWrapper>,
                        res: Response<AccommodationWrapper>
                    ) {
                        if (res.isSuccessful) {
                            val accommodation = res.body()?.data
                            onResult(accommodation)
                        } else {
                            Log.e("JourneyViewModel", "Failed to fetch accommodation details: ${res.message()}")
                            onResult(null)
                        }
                    }

                    override fun onFailure(call: Call<AccommodationWrapper>, t: Throwable) {
                        Log.e("JourneyViewModel", "Error fetching accommodation details: ${t.message}")
                        onResult(null)
                    }
                })
            } catch (e: Exception) {
                Log.e("JourneyViewModel", "Error in getAccommodationDetailsById: ${e.message}")
                onResult(null)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ItineraryApplication)
                val userRepository = application.container.userRepository
                val itineraryDestinationRepository = application.container.itineraryDestinationRepository
                JourneyViewModel(userRepository, itineraryDestinationRepository)
            }
        }
    }

}