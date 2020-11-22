package com.gkgio.data.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersItem
import javax.inject.Inject

class OrdersItemResponseTransformer @Inject constructor(
        private val ordersAddressDataResponseTransformer: OrdersAddressDataResponseTransformer,
        private val ordersMealsDataResponseTransformer: OrdersMealsDataResponseTransformer
) : BaseTransformer<OrdersItemResponse, OrdersItem> {
    override fun transform(data: OrdersItemResponse): OrdersItem = with(data) {
        OrdersItem(
                id,
                clientId,
                cookerId,
                status,
                type,
                ordersAddressDataResponseTransformer.transform(address),
                meals.map { ordersMealsDataResponseTransformer.transform(it) },
                lunches.map { ordersMealsDataResponseTransformer.transform(it) },
                price,
                chatId,
                createdAt,
                unreadMessagesCount
        )
    }

}