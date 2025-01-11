package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.DestinationModel
import com.example.alp_visualprogramming.service.DestinationAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DestinationRepository {
    suspend fun getAllDestinations(token: String): List<DestinationModel>
    suspend fun getDestinationById(token: String, id: Int): DestinationModel
}

class NetworkDestinationRepository(
    private val destinationAPIService: DestinationAPIService
) : DestinationRepository {

    override suspend fun getAllDestinations(token: String): List<DestinationModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = destinationAPIService.getAllDestinations(token)
                response.data // Mengambil daftar destinasi dari respons
            } catch (e: Exception) {
                throw Exception("Failed to fetch destinations: ${e.message}")
            }
        }
    }

    override suspend fun getDestinationById(token: String, id: Int): DestinationModel {
        return withContext(Dispatchers.IO) {
            try {
                val response = destinationAPIService.getDestination(token, id)
                response.data // Mengambil detail destinasi dari respons
            } catch (e: Exception) {
                throw Exception("Failed to fetch destination: ${e.message}")
            }
        }
    }
}
