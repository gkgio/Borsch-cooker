package com.gkgio.domain.own

import com.gkgio.domain.orders.OrdersMealsItemData

data class OwnDashboardData(
    val cookerName: String,
    val activityStatus: Boolean,
    val subscriptionStatus: Boolean?,
    val subscriptionExpirationDate: String?,
    val activeMeals: List<OrdersMealsItemData>,
    val activeLunches: List<OrdersMealsItemData>,
    val delivery: Boolean,
    val diagrammOrders: List<OwnDashboardDiagrammData>,
    val profit: Int,
    val reviews: OwnDashboardReviewsData
)