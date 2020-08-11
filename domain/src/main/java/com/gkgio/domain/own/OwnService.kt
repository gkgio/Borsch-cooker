package com.gkgio.domain.own

import io.reactivex.Single

interface OwnService {
    fun setDutyStatus(isOnDuty: Boolean): Single<OwnDashboardData>
    fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<OwnDashboardData>
    fun setPickupStatus(isPickupAvailable: Boolean): Single<OwnDashboardData>
    fun loadDashboardData(): Single<OwnDashboardData>
}