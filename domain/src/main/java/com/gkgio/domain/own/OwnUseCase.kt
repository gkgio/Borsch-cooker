package com.gkgio.domain.own

import io.reactivex.Single
import javax.inject.Inject

interface OwnUseCase {
    fun setDutyStatus(isOnDuty: Boolean): Single<OwnDashboardData>
    fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<OwnDashboardData>
    fun setPickupStatus(isPickupAvailable: Boolean): Single<OwnDashboardData>
    fun loadDashboardData(): Single<OwnDashboardData>
}

class OwnUseCaseImpl @Inject constructor(
    private val ownService: OwnService
) : OwnUseCase {
    override fun setDutyStatus(isOnDuty: Boolean): Single<OwnDashboardData> =
        ownService.setDutyStatus(isOnDuty)

    override fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<OwnDashboardData> =
        ownService.setDeliveryStatus(isDeliveryAvailable)

    override fun setPickupStatus(isPickupAvailable: Boolean): Single<OwnDashboardData> =
        ownService.setPickupStatus(isPickupAvailable)

    override fun loadDashboardData(): Single<OwnDashboardData> =
        ownService.loadDashboardData()
}