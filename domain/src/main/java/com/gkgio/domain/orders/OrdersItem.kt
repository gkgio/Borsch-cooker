package com.gkgio.domain.orders

data class OrdersItem(
    val id: String,
    val clientId: String,
    val cookerId: String,
    val status: String,
    val address: OrdersAddressItemData,
    val meals: List<OrdersMealsItemData>,
    val lunches: List<OrdersMealsItemData>
)