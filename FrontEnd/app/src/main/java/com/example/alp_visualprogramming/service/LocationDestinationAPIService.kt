package com.example.alp_visualprogramming.service
import com.example.alp_visualprogramming.models.LocationDestinationResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationDestinationAPIService {
    @GET("v1/geocode/search")
    suspend fun searchCity(
        @Query("text") cityName: String,
        @Query("format") format: String = "json",
        @Query("apiKey") apiKey: String
    ): Response<LocationDestinationResponse>
}
