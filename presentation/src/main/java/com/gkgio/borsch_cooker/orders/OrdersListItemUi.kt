package com.gkgio.borsch_cooker.orders

data class OrdersListItemUi(
    val id: Int,
    val clientId: Int,
    val cookerId: Int,
    val status: String,
    val address: OrdersAddressItemUi,
    val meals: List<OrdersMealsItemUi>,
    val lunches: List<OrdersMealsItemUi>
)