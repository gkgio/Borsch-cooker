package com.gkgio.domain.orders

import io.reactivex.Single
import javax.inject.Inject

interface OrdersUseCase {
    fun loadAllOrdersData(): Single<List<OrdersData>>
    fun loadActiveOrdersData(): Single<List<OrdersData>>
}

class OrdersUseCaseImpl @Inject constructor(
    private val ordersService: OrdersService
) : OrdersUseCase {
    override fun loadAllOrdersData(): Single<List<OrdersData>> =
        ordersService.loadAllOrdersData()

    override fun loadActiveOrdersData(): Single<List<OrdersData>> =
        ordersService.loadActiveOrdersData()
}