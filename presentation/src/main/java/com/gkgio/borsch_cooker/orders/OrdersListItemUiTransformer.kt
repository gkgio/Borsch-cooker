package com.gkgio.borsch_cooker.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersItem
import javax.inject.Inject

class OrdersListItemUiTransformer @Inject constructor(
    private val ordersAddressItemUiTransformer: OrdersAddressItemUiTransformer,
    private val ordersMealsItemUiTransformer: OrdersMealsItemUiTransformer
) : BaseTransformer<OrdersItem, OrdersListItemUi> {
    override fun transform(data: OrdersItem) = with(data) {
        OrdersListItemUi(
            id,
            clientId,
            cookerId,
            status,
            ordersAddressItemUiTransformer.transform(address),
            meals.map { ordersMealsItemUiTransformer.transform(it) },
            lunches.map { ordersMealsItemUiTransformer.transform(it) }
        )
    }
}