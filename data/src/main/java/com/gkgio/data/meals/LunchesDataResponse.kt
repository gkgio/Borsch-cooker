package com.gkgio.data.meals

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LunchesDataResponse(
    @Json(name = "lunches")
    val lunches: List<LunchesItemDataResponse>
)