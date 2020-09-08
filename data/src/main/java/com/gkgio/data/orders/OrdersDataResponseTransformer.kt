package com.gkgio.data.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersItem
import javax.inject.Inject

class OrdersDataResponseTransformer @Inject constructor(
    private val ordersItemResponseTransformer: OrdersItemResponseTransformer
) : BaseTransformer<OrdersDataResponse, List<OrdersItem>> {
    override fun transform(data: OrdersDataResponse) = with(data) {
        orders.map { ordersItemResponseTransformer.transform(it) }
    }
}