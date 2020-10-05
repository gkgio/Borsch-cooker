package com.gkgio.borsch_cooker.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.MealsItem
import javax.inject.Inject

class MealsItemUiTransformer @Inject constructor() : BaseTransformer<MealsItem, MealsItemUi> {
    override fun transform(data: MealsItem): MealsItemUi = with(data) {
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