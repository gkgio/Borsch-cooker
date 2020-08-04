package com.gkgio.domain.own

import io.reactivex.Single

interface OwnService {
    fun setDutyStatus(isOnDuty: Boolean): Single<String>
    fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<String>
}