package com.gkgio.borsch_cooker.own

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.own.OwnUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class OwnViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val ownUseCase: OwnUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    init {

    }

    fun setDutyStatus(isOnDuty: Boolean) {
        ownUseCase
            .setDutyStatus(isOnDuty)
            .map { it }
            .applySchedulers()
            .subscribe(
                {
                    Timber.e(it, "Request sent successful")
                },
                {
                    Timber.e(it, "Error send request")
                }
            )
            .addDisposable()
    }

    fun setDeliveryStatus(isDeliveryAvailable: Boolean) {
        ownUseCase
            .setDeliveryStatus(isDeliveryAvailable)
            .map { it }
            .applySchedulers()
            .subscribe(
                {
                    Timber.e(it, "Request sent successful")
                },
                {
                    Timber.e(it, "Error send request")
                }
            )
            .addDisposable()
    }

}