package com.example.alp_visualprogramming

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.alp_visualprogramming.repository.AuthenticationRepository
import com.example.alp_visualprogramming.repository.ItineraryRepository
import com.example.alp_visualprogramming.repository.NetworkAuthenticationRepository
import com.example.alp_visualprogramming.repository.NetworkItineraryRepository
import com.example.alp_visualprogramming.repository.NetworkUserRepository
import com.example.alp_visualprogramming.repository.UserRepository
import com.example.alp_visualprogramming.service.AuthenticationAPIService
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