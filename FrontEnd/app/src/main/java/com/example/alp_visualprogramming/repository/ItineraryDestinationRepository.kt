package com.example.alp_visualprogramming.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alp_visualprogramming.models.AccommodationIdWrapper
import com.example.alp_visualprogramming.models.AccommodationRequest
import com.example.alp_visualprogramming.models.AccommodationWrapper
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryDestinationResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.models.GetItineraryDestinationResponse
import com.example.alp_visualprogramming.models.ItineraryDestinationRequest
import com.example.alp_visualprogramming.models.ItineraryDestinationUpdateRequest
import com.example.alp_visualprogramming.service.DestinationAPIService
import com.example.alp_visualprogramming.service.ItineraryDestinationAPIService
import retrofit2.Call
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


interface ItineraryDestinationRepository{
    fun getAllItineraryDestinations(token: String, itineraryId: Int): Call<GetAllItineraryDestinationResponse>
    fun getItineraryDestination(itineraryDestinationId: Int, token: String): Call<GetItineraryDestinationResponse>
    fun deleteItineraryDestination(itineraryDestinationId: Int, token: String): Call<GeneralResponseModel>
    fun getDestinationById(destinationId: Int, token: String): Call<GetDestinationResponse>
    fun createItineraryDestination(token: String, startDate: String, endDate: String, locationId: Int, accomodationId: Int?, itineraryId: Int): Call<GeneralResponseModel>
    fun updateItineraryDestination(token: String,itineraryDestinationId: Int, startDate: String, endDate: String, locationId: Int, accomodationId: Int?, itineraryId: Int): Call<GeneralResponseModel>

    fun updateJourneyAccommodation(
        token: String,
        itineraryDestinationId: Int,
        accommodationRequest: AccommodationRequest
    ): Call<AccommodationWrapper>
    fun getOrCreateAccommodation(
        token: String,
        placeId: String,
        name: String,
        address: String,
        locationImage: String,
        placeApi: String,
        categories: String,
        cost: String,
        people: Int,
        openingHours: String?,
        website: String?,
        phone: String?
    ): Call<AccommodationWrapper>
    fun checkAccommodation(token: String, itineraryDestinationId: Int): Call<AccommodationIdWrapper>
    fun getAccommodationDetails(token: String, accommodationId: Int): Call<AccommodationWrapper>

}

class NetworkItineraryDestinationRepository(private val itineraryDestinationAPIService: ItineraryDestinationAPIService): ItineraryDestinationRepository{
    override fun getAllItineraryDestinations(token: String,  itineraryId: Int): Call<GetAllItineraryDestinationResponse> {
        return itineraryDestinationAPIService.getAllItineraryDestination(token, itineraryId)
    }
    override fun getDestinationById(destinationId: Int, token: String): Call<GetDestinationResponse> {
        return itineraryDestinationAPIService.getDestinationById(destinationId, token)
    }

    override fun getItineraryDestination(itineraryDestinationId: Int, token: String): Call<GetItineraryDestinationResponse> {
        return itineraryDestinationAPIService.getItineraryDestination(itineraryDestinationId, token)
    }

    override fun deleteItineraryDestination(itineraryDestinationId: Int, token: String): Call<GeneralResponseModel> {
        return itineraryDestinationAPIService.deleteItineraryDestination(token, itineraryDestinationId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createItineraryDestination(token: String, startDate: String, endDate: String, locationId: Int,accomodationId: Int?, itineraryId: Int ): Call<GeneralResponseModel>{
        val startDateT = convertIsoToCustomFormat(startDate)
        val endDateT = convertIsoToCustomFormat(endDate)
        return itineraryDestinationAPIService.createItineraryDestination(token, locationId, ItineraryDestinationRequest(itineraryId, accomodationId, startDateT, endDateT))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun updateItineraryDestination(token: String,itineraryDestinationId: Int, startDate: String, endDate: String, locationId: Int,accomodationId: Int?, itineraryId: Int ): Call<GeneralResponseModel>{
        val startDateT = convertIsoToCustomFormat(startDate)
        val endDateT = convertIsoToCustomFormat(endDate)
        return itineraryDestinationAPIService.updateItineraryDestination(token, itineraryDestinationId, ItineraryDestinationUpdateRequest( accomodation_id =  accomodationId, start_date =  startDateT, end_date =  endDateT, destination_id = locationId))
    }

    override fun getOrCreateAccommodation(
        token: String,
        placeId: String,
        name: String,
        address: String,
        locationImage: String,
        placeApi: String,
        categories: String,
        cost: String,
        people: Int,
        openingHours: String?,
        website: String?,
        phone: String?
    ): Call<AccommodationWrapper> {
        return itineraryDestinationAPIService.getOrCreateAccommodation(
            token = token,
            placeId = placeId,
            name = name,
            address = address,
            locationImage = locationImage,
            placeApi = placeApi,
            categories = categories,
            cost = cost,
            people = people,
            openingHours = openingHours,
            website = website,
            phone = phone
        )
    }

    override fun updateJourneyAccommodation(
        token: String,
        itineraryDestinationId: Int,
        accommodationRequest: AccommodationRequest
    ): Call<AccommodationWrapper> {
        return itineraryDestinationAPIService.updateJourneyAccommodation(
            token = token,
            itineraryDestinationId = itineraryDestinationId,
            accommodationRequest = accommodationRequest // Pass the request body directly
        )
    }

    override fun checkAccommodation(token: String, itineraryDestinationId: Int): Call<AccommodationIdWrapper> {
        return itineraryDestinationAPIService.checkAccommodation(token, itineraryDestinationId)
    }

    override fun getAccommodationDetails(token: String, accommodationId: Int): Call<AccommodationWrapper> {
        return itineraryDestinationAPIService.getAccommodationDetails(token, accommodationId)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun convertIsoToCustomFormat(dateString: String, customFormat: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
            val localDate = LocalDate.parse(dateString, inputFormatter)

            // Set the time to midnight and convert it to UTC time zone
            val zonedDateTime = localDate.atStartOfDay()

            // Convert the ZonedDateTime to a Date object and return it
            val isoDateTime = zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
            "$isoDateTime.000Z"

        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}


