package com.gkgio.borsch_cooker.onboarding

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch_cooker.base.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

}