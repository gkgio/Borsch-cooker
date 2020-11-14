package com.gkgio.borsch_cooker.own.activemeals

import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.LunchesItem
import com.gkgio.domain.meals.MealsItem
import javax.inject.Inject

class ActiveLunchesUiTransformer @Inject constructor() :
    BaseTransformer<LunchesItem, OrdersMealsItemUi> {
    override fun transform(data: LunchesItem): OrdersMealsItemUi = with(data) {
        OrdersMealsItemUi(
            id,
            available,
            calories,
            null,
            null,
            null,
            name,
            portions,
            price,
            weight,
            cookerId,
            images
        )
    }

}