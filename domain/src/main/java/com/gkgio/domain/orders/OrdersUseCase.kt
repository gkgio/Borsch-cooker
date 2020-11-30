package com.gkgio.domain.orders

import io.reactivex.Single
import javax.inject.Inject

interface OrdersUseCase {
    fun loadAllOrdersData(): Single<List<OrdersItem>>
    fun loadActiveOrdersData(): Single<List<OrdersItem>>
    fun loadNewOrdersData(): Single<List<OrdersItem>>
}

class OrdersUseCaseImpl @Inject constructor(
    private val ordersService: OrdersService
) : OrdersUseCase {
    override fun loadAllOrdersData(): Single<List<OrdersItem>> =
        ordersService.loadAllOrdersData()

    override fun loadActiveOrdersData(): Single<List<OrdersItem>> =
        ordersService.loadActiveOrdersData()

    override fun loadNewOrdersData(): Single<List<OrdersItem>> =
        ordersService.loadNewOrdersData()
}