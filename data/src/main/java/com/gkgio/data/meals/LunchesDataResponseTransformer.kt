package com.gkgio.data.meals

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.meals.LunchesItem
import javax.inject.Inject

class LunchesDataResponseTransformer @Inject constructor(
    private val lunchesItemDataResponseTransformer: LunchesItemDataResponseTransformer
) : BaseTransformer<LunchesDataResponse, List<LunchesItem>> {
    override fun transform(data: LunchesDataResponse): List<LunchesItem> = with(data) {
        lunches.map { lunchesItemDataResponseTransformer.transform(it) }
    }
}