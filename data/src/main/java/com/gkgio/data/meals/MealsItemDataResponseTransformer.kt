package com.gkgio.data.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.MealsItem
import javax.inject.Inject

class MealsItemDataResponseTransformer @Inject constructor() :
    BaseTransformer<MealsItemDataResponse, MealsItem> {
    override fun transform(data: MealsItemDataResponse): MealsItem = with(data) {
        MealsItem(
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