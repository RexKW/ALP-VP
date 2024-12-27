package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.service.DestinationDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DestinationRepositories {
    fun getAllDestinations(): List<DestinationModel>
}



class DestinationDBRepositories(private val destinationDBService: DestinationDBService) {


    suspend fun getAllDestinations(): List<DestinationModel>{
        return withContext(Dispatchers.IO){
            destinationDBService.getAllDestinations()
        }
    }


}