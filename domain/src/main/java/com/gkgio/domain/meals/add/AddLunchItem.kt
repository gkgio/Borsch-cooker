package com.gkgio.domain.meals.add

import java.io.File

data class AddLunchItem(
    val available: Boolean,
    val calories: Int,
    val discountPercent: Int,
    val name: String,
    val portions: Int,
    val weight: String,
    val images: List<File?>
)