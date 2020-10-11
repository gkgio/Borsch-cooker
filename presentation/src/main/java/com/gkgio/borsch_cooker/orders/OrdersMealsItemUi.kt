package com.gkgio.borsch_cooker.orders

data class OrdersMealsItemUi(
    val id: String,
    val available: Boolean,
    val calories: Int,
    val cookTime: Int?,
    val description: String?,
    val ingredients: List<String>?,
    val name: String,
    val portions: Int,
    val price: Int,
    val weight: String,
    val cookerId: String,
    val images: List<String>?
)