package com.example.alp_visualprogramming.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.api.ApiClient
import com.example.alp_visualprogramming.api.LocationDestinationClient
import com.example.alp_visualprogramming.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _locationList = MutableStateFlow<List<Location>>(emptyList())
    val locationList: StateFlow<List<Location>> = _locationList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchLocations(categories: String, filter: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("LocationViewModel", "Fetching locations with categories=$categories filter=$filter")
                val response = ApiClient.locationAPIService.getPlaces(categories, filter, limit = 30, apiKey = apiKey)
                Log.d("LocationViewModel", "API Response: ${response.code()} ${response.message()}")

                if (response.isSuccessful) {
                    val features = response.body()?.features ?: emptyList()
                    Log.d("LocationViewModel", "Features received: ${features.size}")

                    // Map API features to your Location model
                    _locationList.value = features.map { feature ->
                        Location(
                            place_id = feature.properties.place_id ?: "Unknown Place ID",
                            name = feature.properties.name ?: "Unknown Name",
                            address = feature.properties.formatted ?: "No Address",
                            categories = feature.properties.categories ?: emptyList(),
                            openingHours = feature.properties.opening_hours,
                            website = feature.properties.website,
                            phone = feature.properties.contact?.phone
                        )
                    }

                    Log.d("LocationViewModel", "Locations updated in _locationList: ${_locationList.value.size}")
                } else {
                    _errorMessage.value = "Failed to load data: ${response.message()} (Code: ${response.code()})"
                    Log.e("LocationViewModel", "Failed to load data: ${response.message()} (Code: ${response.code()})")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching locations: ${e.message}"
                Log.e("LocationViewModel", "Error fetching locations", e)
            }
        }
    }

    fun fetchPlaceIdForCity(cityName: String, apiKey: String, onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("LocationViewModel", "Fetching place_id for city: $cityName")
                val response = LocationDestinationClient.locationdestinationAPIService.searchCity(
                    cityName = cityName,
                    apiKey = apiKey
                )

                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    if (results.isNotEmpty()) {
                        val placeId = results[0].placeId // Assuming we take the first match
                        Log.d("LocationViewModel", "Found place_id: $placeId for city: $cityName")
                        onResult(placeId)
                    } else {
                        Log.e("LocationViewModel", "No place_id found for city: $cityName")
                        onResult(null)
                    }
                } else {
                    Log.e(
                        "LocationViewModel",
                        "Failed to fetch place_id: ${response.message()} (Code: ${response.code()})"
                    )
                    onResult(null)
                }
            } catch (e: Exception) {
                Log.e("LocationViewModel", "Error fetching place_id: ${e.message}", e)
                onResult(null)
            }
        }
    }

//    suspend fun getPlaceId(cityName: String, apiKey: String): String? {
//        val response = apiService.searchCity(cityName = cityName, apiKey = apiKey)
//        return if (response.isSuccessful) {
//            response.body()?.results?.firstOrNull()?.placeId
//        } else {
//            null
//        }
//    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
                return LocationViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
