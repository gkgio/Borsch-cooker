package com.gkgio.domain.orders

import io.reactivex.Single

interface OrdersService {
    fun loadAllOrdersData(): Single<List<OrdersItem>>
    fun loadActiveOrdersData(): Single<List<OrdersItem>>
}