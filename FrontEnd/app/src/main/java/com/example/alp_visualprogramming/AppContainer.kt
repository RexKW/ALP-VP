package com.example.alp_visualprogramming

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.alp_visualprogramming.repository.*
import com.example.alp_visualprogramming.service.AuthenticationService
import com.example.alp_visualprogramming.service.DestinationAPIService
import com.example.alp_visualprogramming.service.ItineraryAPIService
import com.example.alp_visualprogramming.service.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val itineraryRepository: ItineraryRepository
    val destinationRepository: DestinationRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
) : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/"

    private val retrofit by lazy { initRetrofit() }

    // Initialize the Authentication API Service
    private val authenticationAPIService: AuthenticationService by lazy {
        retrofit.create(AuthenticationService::class.java)
    }

    private val userAPIService: UserAPIService by lazy {
        retrofit.create(UserAPIService::class.java)
    }

    private val itineraryAPIService: ItineraryAPIService by lazy {
        retrofit.create(ItineraryAPIService::class.java)
    }

    private val destinationAPIService: DestinationAPIService by lazy {
        retrofit.create(DestinationAPIService::class.java)
    }

    // Updated: Use the correct constructor for AuthenticationRepository
    override val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationRepository(authenticationAPIService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userAPIService)
    }

    override val itineraryRepository: ItineraryRepository by lazy {
        NetworkItineraryRepository(itineraryAPIService)
    }

    override val destinationRepository: DestinationRepository by lazy {
        NetworkDestinationRepository(destinationAPIService)
    }

    private fun initRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
