package com.gkgio.borsch_cooker.orders.offer

import com.gkgio.borsch_cooker.orders.OrdersMealsItemUi
import com.gkgio.borsch_cooker.orders.offer.model.MealItemModel
import com.gkgio.data.BaseTransformer
import javax.inject.Inject

class OrdersToMealsUiTransformer @Inject constructor() :
    BaseTransformer<List<OrdersMealsItemUi>, List<MealItemModel>> {
    override fun transform(data: List<OrdersMealsItemUi>): List<MealItemModel> = with(data) {
        this.map { meals ->
            MealItemModel(
                if (!meals.images.isNullOrEmpty()) meals.images.first() else null,
                meals.name,
                meals.portions
            )
        }
    }

}