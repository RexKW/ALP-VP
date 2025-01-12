package com.example.alp_visualprogramming

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.alp_visualprogramming.repository.ActivityRepository
import com.example.alp_visualprogramming.repository.AuthenticationRepository
import com.example.alp_visualprogramming.repository.DestinationRepository
import com.example.alp_visualprogramming.repository.ItineraryDestinationRepository
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.NetworkActivityRepository
import com.example.alp_visualprogramming.repository.NetworkAuthenticationRepository
import com.example.alp_visualprogramming.repository.NetworkDestinationRepository
import com.example.alp_visualprogramming.repository.NetworkItineraryDestinationRepository
import com.example.alp_visualprogramming.repository.NetworkItineraryRepository
import com.example.alp_visualprogramming.repository.NetworkUserRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.service.ActivityAPIService
import com.example.alp_visualprogramming.service.AuthenticationAPIService
import com.example.alp_visualprogramming.service.DestinationAPIService
import com.example.alp_visualprogramming.service.ItineraryAPIService
import com.example.alp_visualprogramming.service.ItineraryDestinationAPIService
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
    val itineraryDestinationRepository: ItineraryDestinationRepository
    val activityRepository: ActivityRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://10.0.2.2:3000/"


    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val itineraryRetrofitService: ItineraryAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ItineraryAPIService::class.java)
    }

    private val destinationRetrofitService: DestinationAPIService by lazy{
        val retrofit = initRetrofit()

        retrofit.create(DestinationAPIService::class.java)
    }

    private val itineraryDestinationRetrofitService: ItineraryDestinationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ItineraryDestinationAPIService::class.java)
    }

    private val activityRetrofitService: ActivityAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ActivityAPIService::class.java)
    }


    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val itineraryRepository: ItineraryRepository by lazy {
        NetworkItineraryRepository(itineraryRetrofitService)
    }

    override val destinationRepository: DestinationRepository by lazy {
        NetworkDestinationRepository(destinationRetrofitService)
    }

    override val itineraryDestinationRepository: ItineraryDestinationRepository by lazy{
        NetworkItineraryDestinationRepository(itineraryDestinationRetrofitService)

    }

    override val activityRepository: ActivityRepository by lazy {
        NetworkActivityRepository(activityRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}