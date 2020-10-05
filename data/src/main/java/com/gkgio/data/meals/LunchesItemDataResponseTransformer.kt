package com.gkgio.data.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.LunchesItem
import javax.inject.Inject

class LunchesItemDataResponseTransformer @Inject constructor() :
    BaseTransformer<LunchesItemDataResponse, LunchesItem> {
    override fun transform(data: LunchesItemDataResponse): LunchesItem = with(data) {
        LunchesItem(
            id,
            available,
            calories,
            discountPercent,
            name,
            portions,
            price,
            weight,
            cookerId,
            images
        )
    }

}