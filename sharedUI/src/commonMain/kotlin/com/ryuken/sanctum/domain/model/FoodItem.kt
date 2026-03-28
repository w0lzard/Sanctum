package com.ryuken.sanctum.domain.model

data class FoodItem(
    val id : String,
    val name : String,
    val brand : String?,
    val caloriesPer100g : Double,
    val proteinPer100g : Double,
    val fatsPer100g : Double,
    val CarbsPer100g : Double
)
