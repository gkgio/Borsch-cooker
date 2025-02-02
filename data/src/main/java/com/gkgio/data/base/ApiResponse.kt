package com.gkgio.data.base

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class ApiResponse(
    val error: String,
    val status: String
)