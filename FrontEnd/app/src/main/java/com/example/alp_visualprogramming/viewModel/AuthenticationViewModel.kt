package com.example.alp_visualprogramming.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.repository.AuthenticationRepository
import com.example.alp_visualprogramming.service.AuthResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// State untuk Autentikasi
sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    data class Success(val response: AuthResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthenticationViewModel(private val authRepository: AuthenticationRepository) : ViewModel() {
    // StateFlow untuk memantau status autentikasi
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    /**
     * Fungsi untuk melakukan login
     * @param email Alamat email pengguna
     * @param password Kata sandi pengguna
     */
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email dan Password harus diisi")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { response ->
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Login failed")
                }
            )
        }
    }

    /**
     * Fungsi untuk melakukan registrasi
     * @param email Alamat email pengguna
     * @param password Kata sandi pengguna
     */
    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email dan Password harus diisi")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = authRepository.register(email, password)
            result.fold(
                onSuccess = { response ->
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Registration failed")
                }
            )
        }
    }

    /**
     * Fungsi untuk melakukan logout
     */
//    fun logout() {
//        _authState.value = AuthState.Unauthenticated
//    }
//
//    // Factory untuk inisialisasi ViewModel dengan dependensi AuthenticationRepository
//    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                val repository = AuthenticationRepository() // Pastikan ini sesuai dengan inisialisasi Anda
//                return AuthenticationViewModel(repository) as T
//            }
//        }
//    }
}
