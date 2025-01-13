package com.example.alp_visualprogramming.models

data class BudgetModel (
    val id: Int,
    val itinerary_id: Int,
    val estimated_budget: Double,
    val actual_budget: Double,
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
data class ActualBudgetResponseWithData(
    val data: ActualBudgetResponse

)

data class GetBudgetResponse (
    val data: BudgetModel
)

data class GetAllPlannedBudgetResponse (
    val data: List<BudgetModel>
)

data class BudgetRequest (
    val totalAccommodation: Double,
    val totalTransport: Double,
    val totalShoppingEntertainment: Double,
    val totalCulinary: Double,
    val totalHealthcare: Double,
    val totalSightSeeing: Double,
    val totalSport: Double
)