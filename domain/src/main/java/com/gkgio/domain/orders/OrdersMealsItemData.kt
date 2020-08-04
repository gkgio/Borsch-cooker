package com.gkgio.domain.orders

data class OrdersMealsItemData(
    val id: String,
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
    val cookerId: String,
    val lunchId: String,
    val images: List<String>
)