package com.gkgio.data.orderdetails

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.data.orders.OrdersDataResponse
import com.gkgio.data.orders.OrdersDataResponseTransformer
import com.gkgio.domain.orderdetails.OrderDetailsService
import com.gkgio.domain.orders.OrdersData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class OrderDetailsServiceImpl @Inject constructor(
    private val orderDetailsApi: OrderDetailsApi,
    private val ordersDataResponseTransformer: OrdersDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer

) : BaseService(serverExceptionTransformer), OrderDetailsService {
    override fun loadOrderDetailsData(orderId: String): Single<OrdersData> =
        executeRequest(
            orderDetailsApi.loadOrderDetailsData(orderId).map {
                ordersDataResponseTransformer.transform(it)
            }
        )

    interface OrderDetailsApi {
        @GET("orders/{orderId}")
        fun loadOrderDetailsData(
            @Path("orderId") orderId: String
        ): Single<OrdersDataResponse>
    }

}