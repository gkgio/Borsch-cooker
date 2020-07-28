package com.gkgio.domain.orders

data class OrdersData(
    val id: Int,
    val clientId: Int,
    val cookerId: Int,
    val status: String,
    val address: OrdersAddressItemData,
    val meals: List<OrdersMealsItemData>,
    val lunches: List<OrdersMealsItemData>
)