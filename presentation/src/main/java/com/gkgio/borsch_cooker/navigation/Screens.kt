package com.gkgio.borsch_cooker.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.gkgio.borsch_cooker.main.MainFragment
import com.gkgio.borsch_cooker.onboarding.OnboardingFragment
import com.gkgio.borsch_cooker.orderdetails.OrderDetailsFragment
import com.gkgio.borsch_cooker.support.SupportFragment
import com.gkgio.borsch_cooker.utils.IntentUtils
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class MarketScreen(
        private val packageName: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createMarketIntent(packageName)
    }

    class OrderDetailsScreen(
        private val orderId: Int
    ) : SupportAppScreen() {
        override fun getFragment() = OrderDetailsFragment.newInstance(orderId)
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

    object OnboardingFragmentScreen : SupportAppScreen() {
        override fun getFragment() = OnboardingFragment()
    }
}