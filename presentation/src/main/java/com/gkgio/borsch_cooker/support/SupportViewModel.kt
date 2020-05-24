package com.gkgio.borsch_cooker.support

import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch_cooker.base.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {


}