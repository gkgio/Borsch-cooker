package com.gkgio.borsch_cooker.own

import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi

data class OwnDashboardUi(
    val cookerName: String,
    val activityStatus: Boolean,
    val subscriptionStatus: Boolean?,
    val subscriptionExpirationDate: String?,
    val activeMeals: List<OrdersMealsItemUi>,
    val activeLunches: List<OrdersMealsItemUi>,
    val delivery: Boolean,
    val pickup: Boolean,
    val diagrammOrders: List<OwnDashboardDiagrammUi>,
    val profit: Int,
    val reviews: OwnDashboardReviewsUi
)