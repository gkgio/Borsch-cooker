package com.gkgio.domain.orders

import io.reactivex.Single

interface OrdersService {
    fun loadOrdersData(ordersType: String): Single<OrdersData>
}