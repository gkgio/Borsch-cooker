package com.gkgio.data.address.suggestions

import com.gkgio.data.address.suggestions.GeoSuggestionDataResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoSuggestionResponse(
    @Json(name = "data")
    val data: GeoSuggestionDataResponse,
    @Json(name = "value")
    val value: String //то что показывается клиенту
)