package com.gkgio.borsch_cooker.meals

data class MealsItemUi(
    val id: String,
    val available: Boolean,
    val calories: Int,
    val name: String,
    val portions: Int,
    val price: Int,
    val weight: String,
    val images: List<String>
)