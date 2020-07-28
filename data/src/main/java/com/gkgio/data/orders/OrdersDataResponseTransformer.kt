package com.gkgio.data.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersData
import javax.inject.Inject

class OrdersDataResponseTransformer @Inject constructor(
    private val ordersAddressDataResponseTransformer: OrdersAddressDataResponseTransformer,
    private val ordersMealsDataResponseTransformer: OrdersMealsDataResponseTransformer
) : BaseTransformer<OrdersDataResponse, OrdersData> {
    override fun transform(data: OrdersDataResponse) = with(data) {
        OrdersData(
            id,
            clientId,
            cookerId,
            status,
            ordersAddressDataResponseTransformer.transform(address),
            meals.map { ordersMealsDataResponseTransformer.transform(it) },
            lunches.map { ordersMealsDataResponseTransformer.transform(it) }
        )
    }
}