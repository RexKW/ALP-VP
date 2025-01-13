package com.example.alp_visualprogramming.service

import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.UserModel
import com.example.alp_visualprogramming.models.UserRoleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("/users/role/{id}")
    fun getUserRole(@Header("X-API-TOKEN") token: String, @Path("id") id: Int): Call<UserRoleResponse>

    @GET("user/{id}")
    fun getUserById(@Path("id") userId: String): Call<UserModel>

    @GET("user/profile")
    fun getUserProfile(@Header("Authorization") token: String): Call<UserModel>
}