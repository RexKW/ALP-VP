package com.example.alp_visualprogramming.models

data class ActivityModel (
val id: Int,
val name: String,
val description: String,
val start_time:String,
val end_time: String,
val type: String,
val cost: Double,
val location_id: Int,
)

data class GetActivityResponse (
    val data: ActivityModel
)

data class GetAllActivityResponse (
    val data: List<ActivityModel>
)

data class ActivityRequest (
    val name: String,
    val description: String,
    val start_time:String,
    val end_time: String,
    val type: String,
    val cost: Double,
    val location_id: Int,
)