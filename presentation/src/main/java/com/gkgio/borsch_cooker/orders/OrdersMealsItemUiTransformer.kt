package com.gkgio.borsch_cooker.orders

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.OrdersMealsItemData
import javax.inject.Inject

class OrdersMealsItemUiTransformer @Inject constructor() :
    BaseTransformer<OrdersMealsItemData, OrdersMealsItemUi> {
    override fun transform(data: OrdersMealsItemData) = with(data) {
        OrdersMealsItemUi(
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
            cookerId,
            images
        )
    }
}