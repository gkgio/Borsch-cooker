package com.gkgio.data.meals

import com.squareup.moshi.Json

data class LunchesItemDataResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "available")
    val available: Boolean,
    @Json(name = "calories")
    val calories: Int,
    @Json(name = "discount_percent")
    val discountPercent: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "portions")
    val portions: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "weight")
    val weight: String,
    @Json(name = "cooker_id")
    val cookerId: String,
    @Json(name = "images")
    val images: List<String>
)