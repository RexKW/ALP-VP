package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.service.AuthRequest
import com.example.alp_visualprogramming.service.AuthResponse
import com.example.alp_visualprogramming.service.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val authService: AuthenticationService) {

    /**
     * Fungsi untuk melakukan login pengguna
     * @param email Alamat email pengguna
     * @param password Kata sandi pengguna
     * @return Result<AuthResponse> dengan hasil autentikasi
     */
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authService.login(AuthRequest(email, password))
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Fungsi untuk melakukan registrasi pengguna
     * @param email Alamat email pengguna
     * @param password Kata sandi pengguna
     * @return Result<AuthResponse> dengan hasil registrasi
     */
    suspend fun register(email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authService.register(AuthRequest(email, password))
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Fungsi untuk melakukan logout pengguna (opsional jika API mendukung)
     */
}
