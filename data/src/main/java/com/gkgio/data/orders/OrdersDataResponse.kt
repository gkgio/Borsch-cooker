package com.gkgio.data.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersDataResponse(
    @Json(name = "orders")
    val orders: List<OrdersItemResponse>
)