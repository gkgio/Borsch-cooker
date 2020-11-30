package com.gkgio.domain.orderdetails

import com.gkgio.domain.orders.OrdersItem
import io.reactivex.Single
import javax.inject.Inject

interface OrderDetailsUseCase {
    fun loadOrderDetailsData(orderId: String): Single<OrdersItem>
    fun changeOrderStatus(orderId: String, status: String): Single<OrdersItem>
}

class OrderDetailsUseCaseImpl @Inject constructor(
    private val orderDetailsService: OrderDetailsService
) : OrderDetailsUseCase {
    override fun loadOrderDetailsData(orderId: String): Single<OrdersItem> =
        orderDetailsService.loadOrderDetailsData(orderId)

    override fun changeOrderStatus(orderId: String, status: String): Single<OrdersItem> =
        orderDetailsService.changeOrderStatus(orderId, status)
}