package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.service.DestinationAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DestinationDBRepositories(private val destinationDBService: DestinationAPIService) {

    suspend fun getAllDestinations(token: String): List<DestinationModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = destinationDBService.getAllDestinations(token)
                response.data // Mengambil daftar destinasi dari respons
            } catch (e: Exception) {
                throw Exception("Error fetching destinations: ${e.message}")
            }
        }
    }
}
