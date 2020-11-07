package com.gkgio.borsch_cooker.support

import android.content.Context
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.utils.SingleLiveEvent
import com.gkgio.borsch_cooker.utils.isPackageInstalled
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class SupportViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val context: Context,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val openFaq = SingleLiveEvent<Unit>()
    val openChat = SingleLiveEvent<Unit>()
    val chatNeedApp = SingleLiveEvent<Unit>()

    fun onFaqClick() {
        openFaq.call()
    }

    fun onChatClick() {
        if (isPackageInstalled(
                context.getString(R.string.support_fragment_telegram_package),
                context.packageManager
            )
        ) openChat.call() else chatNeedApp.call()
    }

}