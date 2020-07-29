package com.gkgio.borsch_cooker.orders

data class OrdersMealsItemUi(
    val id: Int,
    val available: Boolean,
    val calories: Int,
    val cookTime: Int,
    val description: String,
    val ingredients: List<String>,
    val name: String,
    val portions: Int,
    val price: Int,
    val type: String,
    val weight: String,
    val cookerId: Int,
    val lunchId: Int,
    val images: List<String>
)