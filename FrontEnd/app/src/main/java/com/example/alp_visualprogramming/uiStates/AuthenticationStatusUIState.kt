package com.example.todolistapp.uiStates

import com.example.alp_visualprogramming.models.UserModel

// exhaustive (covers all scenario possibilities) interface
sealed interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    data class Failed(val errorMessage: String): AuthenticationStatusUIState
}