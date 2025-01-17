package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.models.GetAllDestinationResponse
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.models.GetDestinationResponse
import com.example.alp_visualprogramming.service.DestinationAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

interface DestinationRepository {
    fun getAllDestinations(token: String): Call<GetAllDestinationResponse>
    fun getDestinationById(token:String,id: Int): Call<GetDestinationResponse>
}



class NetworkDestinationRepository(private val destinationDBService: DestinationAPIService): DestinationRepository {

    override fun getAllDestinations(token: String): Call<GetAllDestinationResponse>{
        return destinationDBService.getAllDestinations(token)
    }

    override fun getDestinationById(token:String, id: Int): Call<GetDestinationResponse>{
        return destinationDBService.getDestination(token, id)
    }



}