package com.gkgio.borsch_cooker.onboarding.address

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InputAddressViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

}