package com.gkgio.data.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersDataResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "cooker_id")
    val cookerId: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "address")
    val address: OrdersAddressDataResponse,
    @Json(name = "meals")
    val meals: List<OrdersMealsDataResponse>,
    @Json(name = "lunches")
    val lunches: List<OrdersMealsDataResponse>
)