package com.gkgio.domain.own

import io.reactivex.Single
import javax.inject.Inject

interface OwnUseCase {
    fun setDutyStatus(isOnDuty: Boolean): Single<String>
    fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<String>
}

class OwnUseCaseImpl @Inject constructor(
    private val ownService: OwnService
) : OwnUseCase {
    override fun setDutyStatus(isOnDuty: Boolean): Single<String> =
        ownService.setDutyStatus(isOnDuty)

    override fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<String> =
        ownService.setDeliveryStatus(isDeliveryAvailable)
}