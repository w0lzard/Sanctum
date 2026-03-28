package com.ryuken.sanctum.domain.model

data class FoodLog(
    val id : Long,
    val food : FoodItem,
    val quantityGrams : Double,
    val mealType : MealType,
    val logDate: String // "yyyy/MM/dd"
)
