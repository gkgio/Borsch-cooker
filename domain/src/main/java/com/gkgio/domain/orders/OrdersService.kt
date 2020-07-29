package com.gkgio.domain.orders

import io.reactivex.Single

interface OrdersService {
    fun loadAllOrdersData(): Single<List<OrdersData>>
    fun loadActiveOrdersData(): Single<List<OrdersData>>
}