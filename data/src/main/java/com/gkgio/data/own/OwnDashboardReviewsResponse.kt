package com.gkgio.data.own

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnDashboardReviewsResponse(
    @Json(name = "total_reviews")
    val totalReviews: Int,
    @Json(name = "average_rating")
    val averageRating: String?
)