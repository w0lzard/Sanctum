package com.ryuken.sanctum.domain.model

data class UserGoals(
    val caloriesKcal : Int = 2000,
    val proteinG : Int = 150,
    val carbsG : Int = 220,
    val fatsG : Int = 70
)
