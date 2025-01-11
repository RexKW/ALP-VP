package com.example.alp_visualprogramming.service

import retrofit2.http.Body
import retrofit2.http.POST

// Data Class untuk Permintaan dan Respons Autentikasi
data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val userId: String)

// Interface untuk API Autentikasi
interface AuthenticationService {

    /**
     * Endpoint untuk Login Pengguna
     * @param request Permintaan login berisi email dan password
     * @return AuthResponse yang berisi token dan ID pengguna
     */
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    /**
     * Endpoint untuk Registrasi Pengguna
     * @param request Permintaan registrasi berisi email dan password
     * @return AuthResponse yang berisi token dan ID pengguna
     */
    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

//    /**
//     * Endpoint untuk Logout Pengguna
//     * @param token Token autentikasi pengguna
//     */
//    @POST("auth/logout")
//    suspend fun logout(@Header("Authorization") token: String)
}
