package com.gkgio.data.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersItemResponse(
    @Json(name = "order_id")
    val id: String,
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "cooker_id")
    val cookerId: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "_type")
    val type: String,
    @Json(name = "address")
    val address: OrdersAddressDataResponse,
    @Json(name = "meals")
    val meals: List<OrdersMealsDataResponse>,
    @Json(name = "lunches")
    val lunches: List<OrdersMealsDataResponse>,
    @Json(name = "order_price")
    val price: Int,
    @Json(name = "chat_id")
    val chatId: Int,
    @Json(name = "created_at")
    val createdAt: String
)