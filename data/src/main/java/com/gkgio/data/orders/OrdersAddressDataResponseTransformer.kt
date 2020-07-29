package com.gkgio.data.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersAddressItemData
import javax.inject.Inject

class OrdersAddressDataResponseTransformer @Inject constructor() :
    BaseTransformer<OrdersAddressDataResponse, OrdersAddressItemData> {
    override fun transform(data: OrdersAddressDataResponse) = with(data) {
        OrdersAddressItemData(
            id,
            area,
            block,
            city,
            cityDistrict,
            country,
            flat,
            floor,
            house,
            intercom,
            region,
            street,
            ownerId,
            ownerType,
            createdAt,
            updatedAt,
            main
        )
    }
}