package com.gkgio.domain.orderdetails

import com.gkgio.domain.orders.OrdersData
import io.reactivex.Single
import javax.inject.Inject

interface OrderDetailsUseCase {
    fun loadOrderDetailsData(orderId: Int): Single<OrdersData>
}

class OrderDetailsUseCaseImpl @Inject constructor(
    private val orderDetailsService: OrderDetailsService
) : OrderDetailsUseCase {
    override fun loadOrderDetailsData(orderId: Int): Single<OrdersData> =
        orderDetailsService.loadOrderDetailsData(orderId)
}