package com.example.alp_visualprogramming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_visualprogramming.models.UserModel
import com.example.alp_visualprogramming.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {

    // StateFlow untuk username
    private val _username = MutableStateFlow<String>("")
    val username: StateFlow<String> get() = _username

    // StateFlow untuk account data
    private val _accountResponse = MutableStateFlow<UserModel?>(null)
    val accountResponse: StateFlow<UserModel?> get() = _accountResponse

    // StateFlow untuk error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // StateFlow untuk loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        // Ambil username saat ViewModel dibuat
        viewModelScope.launch {
            try {
                // Mengumpulkan nilai currentUsername dari UserRepository
                userRepository.currentUsername.collect { username ->
                    _username.value = username // Update _username dengan nilai yang diterima
                }
            } catch (e: Exception) {
                _error.value = e.message  // Menangani error jika terjadi
            }
        }
    }

    fun saveUsername(newUsername: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Simpan username menggunakan repository
                userRepository.saveUsername(newUsername)
                _username.value = newUsername
            } catch (e: Exception) {
                _error.value = e.message  // Menangani error jika terjadi
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAccountData(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Panggil repository untuk mendapatkan data akun
                val response = userRepository.getUserById(userId).execute()
                if (response.isSuccessful) {
                    response.body()?.let { account ->
                        // Menyimpan data akun pada StateFlow
                        _accountResponse.value = UserModel(
                            id = account.id,
                            username = account.username,
                            email = account.email
                        )
                        _username.value = account.username
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
}
