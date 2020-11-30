package com.gkgio.domain.orders

data class OrdersItem(
        val id: String,
        val clientId: String,
        val cookerId: String,
        val status: String,
        val type: String,
        val address: OrdersAddressItemData?,
        val meals: List<OrdersMealsItemData>,
        val lunches: List<OrdersMealsItemData>,
        val price: Int,
        val chatId: Int,
        val createdAt: String,
        val unreadMessagesCount: Int
)