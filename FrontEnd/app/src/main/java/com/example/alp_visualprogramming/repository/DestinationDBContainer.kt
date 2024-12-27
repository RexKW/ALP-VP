package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.service.DestinationDBService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DestinationDBContainer{
    private val BASE_URL = "http://10.0.2.2/"

    private val client = OkHttpClient.Builder()
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofitService: DestinationDBService by lazy {
        retrofit.create(DestinationDBService::class.java)
    }

    val destinationDBRepositories: DestinationDBRepositories by lazy {
        DestinationDBRepositories(retrofitService)
    }
}