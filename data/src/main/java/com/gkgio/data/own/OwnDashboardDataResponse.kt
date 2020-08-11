package com.gkgio.data.own

import com.gkgio.data.orders.OrdersMealsDataResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnDashboardDataResponse(
    @Json(name = "cooker_name")
    val cookerName: String,
    @Json(name = "activity_status")
    val activityStatus: Boolean,
    @Json(name = "subscription_status")
    val subscriptionStatus: Boolean?,
    @Json(name = "subscription_expiration_date")
    val subscriptionExpirationDate: String?,
    @Json(name = "active_meals")
    val activeMeals: List<OrdersMealsDataResponse>,
    @Json(name = "active_lunches")
    val activeLunches: List<OrdersMealsDataResponse>,
    @Json(name = "delivery")
    val delivery: Boolean,
    @Json(name = "pickup")
    val pickup: Boolean,
    @Json(name = "diagramm_orders")
    val diagrammOrders: List<OwnDashboardDiagrammResponse>,
    @Json(name = "profit")
    val profit: Int,
    @Json(name = "reviews")
    val reviews: OwnDashboardReviewsResponse
)