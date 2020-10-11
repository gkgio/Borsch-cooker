package com.gkgio.borsch_cooker.own

import com.gkgio.borsch_cooker.utils.convertValueToDecimal
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.own.OwnDashboardReviewsData
import javax.inject.Inject

class OwnDashboardReviewsUiTransformer @Inject constructor() :
    BaseTransformer<OwnDashboardReviewsData, OwnDashboardReviewsUi> {
    override fun transform(data: OwnDashboardReviewsData) = with(data) {
        OwnDashboardReviewsUi(
            totalReviews,
            convertValueToDecimal(averageRating)
        )
    }

}