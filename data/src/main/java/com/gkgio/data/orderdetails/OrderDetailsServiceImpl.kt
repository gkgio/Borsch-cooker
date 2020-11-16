package com.gkgio.data.orderdetails

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.data.orders.OrdersItemResponse
import com.gkgio.data.orders.OrdersItemResponseTransformer
import com.gkgio.domain.orderdetails.OrderDetailsService
import com.gkgio.domain.orders.OrdersItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class OrderDetailsServiceImpl @Inject constructor(
    private val orderDetailsApi: OrderDetailsApi,
    private val ordersItemResponseTransformer: OrdersItemResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer

) : BaseService(serverExceptionTransformer), OrderDetailsService {
    override fun loadOrderDetailsData(orderId: String): Single<OrdersItem> =
        executeRequest(
            orderDetailsApi.loadOrderDetailsData(orderId).map {
                ordersItemResponseTransformer.transform(it)
            }
        )

    override fun changeOrderStatus(orderId: String, status: String): Single<OrdersItem> =
        executeRequest(
            orderDetailsApi.changeOrderStatus(orderId, status).map {
                ordersItemResponseTransformer.transform(it)
            }
        )

    interface OrderDetailsApi {
        @GET("cooker/orders/{orderId}")
        fun loadOrderDetailsData(
            @Path("orderId") orderId: String
        ): Single<OrdersItemResponse>

        @POST("cooker/orders/{orderId}/change_status")
        fun changeOrderStatus(
            @Path("orderId") orderId: String, @Query("status") status: String
        ): Single<OrdersItemResponse>
    }

}