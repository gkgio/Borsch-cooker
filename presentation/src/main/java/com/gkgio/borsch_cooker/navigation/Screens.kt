package com.gkgio.borsch_cooker.navigation

import android.content.Context
import com.gkgio.borsch_cooker.main.MainFragment
import com.gkgio.borsch_cooker.onboarding.OnboardingFragment
import com.gkgio.borsch_cooker.support.SupportFragment
import com.gkgio.borsch_cooker.support.about.AboutUsFragment
import com.gkgio.borsch_cooker.utils.IntentUtils
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class MarketScreen(
        private val packageName: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createMarketIntent(packageName)
    }

    object SettingsFragmentScreen : SupportAppScreen() {
        override fun getFragment() = SupportFragment()
    }

    object MainFragmentScreen : SupportAppScreen() {
        override fun getFragment() = MainFragment()
    }

    class EmailScreen(
        private val email: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createEmailIntent(email)
    }

    object AboutUsFragmentScreen : SupportAppScreen() {
        override fun getFragment() = AboutUsFragment()
    }

    object OnboardingFragmentScreen : SupportAppScreen() {
        override fun getFragment() = OnboardingFragment()
    }
}