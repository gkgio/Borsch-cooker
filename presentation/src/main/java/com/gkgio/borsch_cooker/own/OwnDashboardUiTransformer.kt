package com.gkgio.borsch_cooker.own

import com.gkgio.borsch_cooker.orders.OrdersMealsItemUiTransformer
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.own.OwnDashboardData
import javax.inject.Inject

class OwnDashboardUiTransformer @Inject constructor(
    private val ownsMealsItemUiTransformer: OrdersMealsItemUiTransformer,
    private val ownDashboardDiagrammUiTransformer: OwnDashboardDiagrammUiTransformer,
    private val ownDashboardReviewsUiTransformer: OwnDashboardReviewsUiTransformer
) : BaseTransformer<OwnDashboardData, OwnDashboardUi> {
    override fun transform(data: OwnDashboardData) = with(data) {
        OwnDashboardUi(
            cookerName,
            activityStatus,
            subscriptionStatus,
            subscriptionExpirationDate,
            activeMeals.map { ownsMealsItemUiTransformer.transform(it) },
            activeLunches.map { ownsMealsItemUiTransformer.transform(it) },
            delivery,
            pickup,
            diagrammOrders.map { ownDashboardDiagrammUiTransformer.transform(it) },
            profit,
            ownDashboardReviewsUiTransformer.transform(data.reviews)
        )
    }
}