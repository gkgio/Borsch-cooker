package com.gkgio.borsch_cooker.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersAddressItemData
import javax.inject.Inject

class OrdersAddressItemUiTransformer @Inject constructor() :
        BaseTransformer<OrdersAddressItemData?, OrdersAddressItemUi?> {
    override fun transform(data: OrdersAddressItemData?) = with(data) {
        if (this != null) OrdersAddressItemUi(
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
        ) else null
    }
}