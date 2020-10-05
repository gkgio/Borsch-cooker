package com.gkgio.data.meals

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealsItemDataResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "available")
    val available: Boolean,
    @Json(name = "calories")
    val calories: Int,
    @Json(name = "cook_time")
    val cookTime: Int,
    @Json(name = "description")
    val description: String,
    @Json(name = "ingredients")
    val ingredients: List<String>,
    @Json(name = "name")
    val name: String,
    @Json(name = "portions")
    val portions: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "weight")
    val weight: String,
    @Json(name = "cooker_id")
    val lunchId: String,
    @Json(name = "images")
    val images: List<String>
)