package com.gkgio.data.meals

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealsDataResponse(
    @Json(name = "meals")
    val meals: List<MealsItemDataResponse>
)