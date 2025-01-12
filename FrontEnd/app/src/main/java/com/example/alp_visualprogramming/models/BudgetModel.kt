package com.example.alp_visualprogramming.models

data class BudgetModel (
    val id: Int,
    val itinerary_id: Int,
    val estimatedBudget: Double,
    val actualBudget: Double,
    val type: String,
)

data class ActualBudgetResponse(
    val totalAccommodation: Double,
    val totalTransport: Double,
    val totalShoppingEntertainment: Double,
    val totalCulinary: Double,
    val totalHealthcare: Double,
    val totalSightSeeing: Double,
    val totalSport: Double
)

data class GetBudgetResponse (
    val data: BudgetModel
)

data class GetAllPlannedBudgetResponse (
    val data: List<BudgetModel>
)

data class BudgetRequest (
    val accommodation: Double,
    val transport: Double,
    val shoppingEntertainment: Double,
    val culinary: Double,
    val healthcare: Double,
    val sightSeeing: Double,
    val sport: Double,
)