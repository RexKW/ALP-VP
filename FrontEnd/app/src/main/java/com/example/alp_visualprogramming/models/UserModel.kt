package com.example.alp_visualprogramming.models

data class UserModel(
    val id: String,
    val username: String,
    val email: String
)

data class UserResponse(
    val data: UserModel
)

data class RegisterRequestModel(
    val username: String,
    val email: String,
    val password: String
)

data class RegisterResponseModel(
    val id: String,
    val username: String,
    val email: String,
    val token: String
)

data class LoginRequestModel(
    val email: String,
    val password: String
)

data class LoginResponseModel(
    val id: String,
    val username: String,
    val email: String,
    val token: String
)
