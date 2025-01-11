package com.example.alp_visualprogramming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.models.*
import com.example.alp_visualprogramming.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerResponse = MutableLiveData<UserModel>()
    val registerResponse: LiveData<UserModel> get() = _registerResponse

    private val _loginResponse = MutableLiveData<UserModel>()
    val loginResponse: LiveData<UserModel> get() = _loginResponse

    private val _logoutResponse = MutableLiveData<GeneralResponseModel>()
    val logoutResponse: LiveData<GeneralResponseModel> get() = _logoutResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun register(registerRequestModel: RegisterRequestModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Panggil repository untuk register
                val response = userRepository.register(registerRequestModel).execute()
                if (response.isSuccessful) {
                    response.body()?.let { registerResponse ->
                        val userModel = UserModel(
                            id = registerResponse.id,
                            username = registerResponse.username,
                            email = registerResponse.email
                        )
                        _registerResponse.value = userModel
                        userRepository.saveUserToken(registerResponse.token)
                        userRepository.saveUsername(registerResponse.username)
                    }
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(loginRequestModel: LoginRequestModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Panggil repository untuk login
                val response = userRepository.login(loginRequestModel).execute()
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        val userModel = UserModel(
                            id = loginResponse.id,
                            username = loginResponse.username,
                            email = loginResponse.email
                        )
                        _loginResponse.value = userModel
                        userRepository.saveUserToken(loginResponse.token)
                        userRepository.saveUsername(loginResponse.username)
                    }
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Panggil repository untuk logout
                val response = userRepository.logout(token).execute()
                if (response.isSuccessful) {
                    _logoutResponse.value = response.body()
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
