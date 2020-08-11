package com.gkgio.data.own

import com.gkgio.data.BaseTransformer
import com.gkgio.data.orders.OrdersMealsDataResponseTransformer
import com.gkgio.domain.own.OwnDashboardData
import javax.inject.Inject

class OwnDashboardDataResponseTransformer @Inject constructor(
    private val ownDashboardDiagrammTransformer: OwnDashboardDiagrammTransformer,
    private val ownDashboardReviewsTransformer: OwnDashboardReviewsTransformer,
    private val ownMealsDataResponseTransformer: OrdersMealsDataResponseTransformer
) : BaseTransformer<OwnDashboardDataResponse, OwnDashboardData> {
    override fun transform(data: OwnDashboardDataResponse) = with(data) {
        OwnDashboardData(
            cookerName,
            activityStatus,
            subscriptionStatus,
            subscriptionExpirationDate,
            activeMeals.map { ownMealsDataResponseTransformer.transform(it) },
            activeLunches.map { ownMealsDataResponseTransformer.transform(it) },
            delivery,
            pickup,
            diagrammOrders.map { ownDashboardDiagrammTransformer.transform(it) },
            profit,
            ownDashboardReviewsTransformer.transform(data.reviews)
        )
    }
}