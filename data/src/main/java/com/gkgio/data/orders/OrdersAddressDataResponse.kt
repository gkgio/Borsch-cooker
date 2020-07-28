package com.gkgio.data.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersAddressDataResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "area")
    val area: String,
    @Json(name = "block")
    val block: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "city_district")
    val cityDistrict: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "flat")
    val flat: String,
    @Json(name = "floor")
    val floor: String,
    @Json(name = "house")
    val house: String,
    @Json(name = "intercom")
    val intercom: String,
    @Json(name = "region")
    val region: String,
    @Json(name = "street")
    val street: String,
    @Json(name = "owner_id")
    val ownerId: Int,
    @Json(name = "owner_type")
    val ownerType: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "main")
    val main: Boolean
)
