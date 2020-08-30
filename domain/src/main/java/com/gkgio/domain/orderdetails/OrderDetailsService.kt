package com.gkgio.domain.orderdetails

import com.gkgio.domain.orders.OrdersItem
import io.reactivex.Single

interface OrderDetailsService {
    fun loadOrderDetailsData(orderId: String): Single<OrdersItem>
}