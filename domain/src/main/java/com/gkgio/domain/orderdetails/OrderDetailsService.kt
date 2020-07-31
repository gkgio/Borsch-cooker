package com.gkgio.domain.orderdetails

import com.gkgio.domain.orders.OrdersData
import io.reactivex.Single

interface OrderDetailsService {
    fun loadOrderDetailsData(orderId: Int): Single<OrdersData>
}