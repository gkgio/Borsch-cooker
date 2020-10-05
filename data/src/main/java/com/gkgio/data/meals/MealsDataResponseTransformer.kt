package com.gkgio.data.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.MealsItem
import javax.inject.Inject

class MealsDataResponseTransformer @Inject constructor(
    private val mealsItemDataResponseTransformer: MealsItemDataResponseTransformer
) :
    BaseTransformer<MealsDataResponse, List<MealsItem>> {
    override fun transform(data: MealsDataResponse): List<MealsItem> = with(data) {
        meals.map { mealsItemDataResponseTransformer.transform(it) }
    }

}