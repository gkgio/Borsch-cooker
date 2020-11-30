package com.gkgio.borsch_cooker.orders

import android.content.Context
import com.gkgio.borsch_cooker.utils.getOrderDeliveryUiTypeByType
import com.gkgio.borsch_cooker.utils.getOrdersStatusNameByOrdersStatus
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersItem
import javax.inject.Inject

class OrdersListItemUiTransformer @Inject constructor(
        private val ordersAddressItemUiTransformer: OrdersAddressItemUiTransformer,
        private val ordersMealsItemUiTransformer: OrdersMealsItemUiTransformer,
        private val context: Context
) : BaseTransformer<OrdersItem, OrdersListItemUi> {
    override fun transform(data: OrdersItem) = with(data) {
        OrdersListItemUi(
                id,
                clientId,
                cookerId,
                status,
                getOrdersStatusNameByOrdersStatus(context, status),
                type,
                getOrderDeliveryUiTypeByType(context, type),
                ordersAddressItemUiTransformer.transform(address),
                meals.map { ordersMealsItemUiTransformer.transform(it) },
                lunches.map { ordersMealsItemUiTransformer.transform(it) },
                price,
                chatId,
                createdAt,
                unreadMessagesCount
        )
    }
}