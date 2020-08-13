package com.gkgio.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiResponse(
    @Json(name = "errors")
    val error: String
)