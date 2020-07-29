package com.gkgio.borsch_cooker.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersData
import javax.inject.Inject

class OrdersListItemUiTransformer @Inject constructor(
    private val ordersAddressItemUiTransformer: OrdersAddressItemUiTransformer,
    private val ordersMealsItemUiTransformer: OrdersMealsItemUiTransformer
) : BaseTransformer<OrdersData, OrdersListItemUi> {
    override fun transform(data: OrdersData) = with(data) {
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