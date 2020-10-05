package com.gkgio.domain.meals

data class LunchesItem(
    val id: String,
    val available: Boolean,
    val calories: Int,
    val discountPercent: Int,
    val name: String,
    val portions: Int,
    val price: Int,
    val weight: String,
    val cookerId: String,
    val images: List<String>
)