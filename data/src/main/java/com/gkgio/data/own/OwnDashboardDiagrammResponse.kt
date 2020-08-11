package com.gkgio.data.own

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnDashboardDiagrammResponse(
    @Json(name = "date")
    val date: String,
    @Json(name = "count")
    val count: Int
)