package com.gkgio.data.own

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.own.OwnDashboardDiagrammData
import com.gkgio.domain.own.OwnDashboardReviewsData
import javax.inject.Inject

class OwnDashboardReviewsTransformer @Inject constructor() :
    BaseTransformer<OwnDashboardReviewsResponse, OwnDashboardReviewsData> {
    override fun transform(data: OwnDashboardReviewsResponse) = with(data) {
        OwnDashboardReviewsData(
            totalReviews,
            averageRating
        )
    }

}