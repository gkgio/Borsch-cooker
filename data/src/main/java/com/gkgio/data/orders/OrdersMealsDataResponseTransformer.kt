package com.gkgio.data.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersMealsItemData
import javax.inject.Inject

class OrdersMealsDataResponseTransformer @Inject constructor() :
    BaseTransformer<OrdersMealsDataResponse, OrdersMealsItemData> {
    override fun transform(data: OrdersMealsDataResponse) = with(data) {
        OrdersMealsItemData(
            id,
            available,
            calories,
            cookTime,
            description,
            ingredients,
            name,
            portions,
            price,
            weight,
            lunchId,
            images
        )
    }
}