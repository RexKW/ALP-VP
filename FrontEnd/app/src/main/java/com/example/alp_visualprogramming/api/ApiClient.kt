package com.example.alp_visualprogramming.api


import com.example.alp_visualprogramming.service.LocationAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.geoapify.com/v2/" // Base URL ends with /

    val locationAPIService: LocationAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationAPIService::class.java)
    }
}
