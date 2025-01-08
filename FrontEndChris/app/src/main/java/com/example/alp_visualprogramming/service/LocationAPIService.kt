package com.example.alp_visualprogramming.service
import com.example.alp_visualprogramming.models.LocationResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationAPIService {
    @GET("places")
    suspend fun getPlaces(
        // @Query("categories") categories: String = "entertainment,commercial", // Default value from the URL
        @Query("categories") categories: String = "accommodation", // Default value from the URL
        @Query("filter") filter: String = "place:519c470f8d382f5c405952f75c01e0fb1cc0f00101f90146847d0000000000c00208", // Default filter from the URL
        @Query("limit") limit: Int = 30, // Default limit from the URL
        @Query("apiKey") apiKey: String = "c9b67c73febc4f71baad197651185143" // The API key must be provided dynamically
    ): Response<LocationResponse>
}