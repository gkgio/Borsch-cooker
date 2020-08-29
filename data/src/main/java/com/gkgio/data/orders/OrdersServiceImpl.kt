package com.gkgio.data.orders

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.orders.OrdersItem
import com.gkgio.domain.orders.OrdersService
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Inject

class OrdersServiceImpl @Inject constructor(
    private val ordersApi: OrdersApi,
    private val ordersDataResponseTransformer: OrdersDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), OrdersService {

    override fun loadAllOrdersData(): Single<List<OrdersItem>> =
        executeRequest(
            ordersApi.loadAllOrdersData().map { ordersList ->
                ordersDataResponseTransformer.transform(ordersList)
            }
        )

    override fun loadActiveOrdersData(): Single<List<OrdersItem>> =
        executeRequest(
            ordersApi.loadActiveOrdersData().map { ordersList ->
                ordersDataResponseTransformer.transform(ordersList)
            }
        )

    interface OrdersApi {
        @GET("orders")
        fun loadAllOrdersData(): Single<OrdersDataResponse>

        @GET("active_orders")
        fun loadActiveOrdersData(): Single<OrdersDataResponse>
    }

}