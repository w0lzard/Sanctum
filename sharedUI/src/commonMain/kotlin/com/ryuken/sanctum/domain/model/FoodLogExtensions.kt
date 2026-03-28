package com.ryuken.sanctum.domain.model

fun List<FoodLog>.toMacroSummary() = MacroSummary(
    proteinG = this.sumOf { it.food.proteinPer100g * (it.quantityGrams / 100) },
    fatsG = this.sumOf { it.food.fatsPer100g * (it.quantityGrams / 100) },
    carbsG = this.sumOf { it.food.CarbsPer100g * (it.quantityGrams / 100) }
)