package com.example.alp_visualprogramming.api

import com.example.alp_visualprogramming.service.LocationDestinationAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocationDestinationClient {
    private const val BASE_URL = "https://api.geoapify.com/"

    val locationdestinationAPIService: LocationDestinationAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationDestinationAPIService::class.java)
    }
}
