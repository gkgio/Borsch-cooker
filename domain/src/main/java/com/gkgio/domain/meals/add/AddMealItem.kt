package com.gkgio.domain.meals.add

import java.io.File

data class AddMealItem(
    val available: Boolean,
    val calories: Int,
    val cookTime: Int,
    val description: String,
    val ingredients: List<String>,
    val name: String,
    val portions: Int,
    val price: Int,
    val weight: Int,
    val images: List<File?>
)