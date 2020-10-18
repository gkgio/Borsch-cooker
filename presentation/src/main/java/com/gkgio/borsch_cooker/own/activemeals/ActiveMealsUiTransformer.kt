package com.gkgio.borsch_cooker.own.activemeals

import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.MealsItem
import javax.inject.Inject

class ActiveMealsUiTransformer @Inject constructor() :
    BaseTransformer<MealsItem, OrdersMealsItemUi> {
    override fun transform(data: MealsItem): OrdersMealsItemUi = with(data) {
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