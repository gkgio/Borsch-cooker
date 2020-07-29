package com.gkgio.data.orders

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.orders.OrdersData
import com.gkgio.domain.orders.OrdersService
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Inject

class OrdersServiceImpl @Inject constructor(
    private val ordersApi: OrdersApi,
    private val ordersDataResponseTransformer: OrdersDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), OrdersService {

    override fun loadAllOrdersData(): Single<List<OrdersData>> =
        executeRequest(
            ordersApi.loadAllOrdersData().map { ordersList ->
                ordersList.map { ordersDataResponseTransformer.transform(it) }
            }
        )

    override fun loadActiveOrdersData(): Single<List<OrdersData>> =
        executeRequest(
            ordersApi.loadAllOrdersData().map { ordersList ->
                ordersList.map { ordersDataResponseTransformer.transform(it) }
            }
        )

    interface OrdersApi {
        @GET("orders")
        fun loadAllOrdersData(): Single<List<OrdersDataResponse>>

        @GET("active_orders")
        fun loadActiveOrdersData(): Single<List<OrdersDataResponse>>
    }

}