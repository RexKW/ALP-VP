package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.*
import retrofit2.Call
import retrofit2.http.*

interface UserAPIService {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequestModel): Call<LoginResponseModel>

    @POST("auth/register")
    fun register(@Body registerRequest: RegisterRequestModel): Call<RegisterResponseModel>

    @POST("auth/logout")
    fun logout(@Header("Authorization") token: String): Call<GeneralResponseModel>

    @GET("user/{id}")
    fun getUserById(@Path("id") userId: String): Call<UserModel>

    @GET("user/profile")
    fun getUserProfile(@Header("Authorization") token: String): Call<UserModel>
}
