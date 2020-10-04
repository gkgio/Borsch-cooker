package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "email")
    val email: String?,
    @Json(name = "banned")
    val banned: Boolean? = false,
    @Json(name = "certification_pending")
    val certificationPending: Boolean? = false,
    @Json(name = "certified")
    val certified: Boolean? = false,
    @Json(name = "commission")
    val commission: String?,
    @Json(name = "delivery")
    val delivery: Boolean? = false,
    @Json(name = "description")
    val description: String?,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)