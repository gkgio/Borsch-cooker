package com.gkgio.borsch_cooker.orders

data class OrdersListItemUi(
    val id: String,
    val clientId: String,
    val cookerId: String,
    val status: String,
    val address: OrdersAddressItemUi,
    val meals: List<OrdersMealsItemUi>,
    val lunches: List<OrdersMealsItemUi>
)