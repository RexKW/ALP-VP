package com.example.alp_visualprogramming.models

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
    val token: String?
)

data class UserRoleResponse(
    val data: String
)