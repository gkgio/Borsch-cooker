package com.gkgio.data.orders

import com.gkgio.domain.orders.OrdersData
import com.gkgio.domain.orders.OrdersService
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class OrdersServiceImpl @Inject constructor(
    private val ordersApi: OrdersApi,
    private val ordersDataResponseTransformer: OrdersDataResponseTransformer
): OrdersService {
    override fun loadOrdersData(ordersType: String): Single<OrdersData> = ordersApi.loadOrdersData(ordersType).map {
        ordersDataResponseTransformer.transform(it)
    }

    interface OrdersApi {
        @GET("orders/")
        fun loadOrdersData(
            @Path("ordersType") ordersType: String
        ): Single<OrdersDataResponse>
    }

}