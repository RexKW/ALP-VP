package com.example.alp_visualprogramming.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.UserModel
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.service.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

    fun logout(token: String): Call<GeneralResponseModel>

    suspend fun saveUserToken(token: String)

    suspend fun saveUsername(username: String)

    fun getUserRole(token: String, id: Int): Call<UserRoleResponse>

    fun getUserById(userId: String): Call<UserModel>

    // Fungsi untuk mendapatkan profil pengguna
    fun getUserProfile(token: String): Call<UserModel>
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Unknown"
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userAPIService.logout(token)
    }

    override fun getUserRole(token: String, id: Int): Call<UserRoleResponse> {
        return userAPIService.getUserRole(token, id)
    }

    override fun getUserById(userId: String): Call<UserModel> {
        return userAPIService.getUserById(userId)
    }

    override fun getUserProfile(token: String): Call<UserModel> {
        return userAPIService.getUserProfile(token)
    }
}