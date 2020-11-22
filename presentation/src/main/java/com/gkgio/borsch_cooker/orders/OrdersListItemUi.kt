package com.gkgio.borsch_cooker.orders

import java.io.Serializable

data class OrdersListItemUi(
        val id: String,
        val clientId: String,
        val cookerId: String,
        val status: String,
        val type: String,
        val address: OrdersAddressItemUi?,
        val meals: List<OrdersMealsItemUi>,
        val lunches: List<OrdersMealsItemUi>,
        val price: Int,
        val chatId: Int,
        val createdAt: String,
        val unreadMessagesCount: Int
) : Serializable