package com.gkgio.borsch_cooker.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.LunchesItem
import javax.inject.Inject

class LunchesItemUiTransformer @Inject constructor() : BaseTransformer<LunchesItem, MealsItemUi> {
    override fun transform(data: LunchesItem): MealsItemUi = with(data) {
        MealsItemUi(
            id,
            available,
            calories,
            name,
            portions,
            price,
            weight,
            images
        )
    }

}