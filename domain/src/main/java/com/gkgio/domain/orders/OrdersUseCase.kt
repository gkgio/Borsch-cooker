package com.gkgio.domain.orders

import io.reactivex.Single
import javax.inject.Inject

interface OrdersUseCase {
    fun loadOrdersData(ordersType: String): Single<OrdersData>
}

class OrdersUseCaseImpl @Inject constructor(
    private val ordersService: OrdersService
) : OrdersUseCase {
    override fun loadOrdersData(ordersType: String): Single<OrdersData> =
        ordersService.loadOrdersData(ordersType)
}