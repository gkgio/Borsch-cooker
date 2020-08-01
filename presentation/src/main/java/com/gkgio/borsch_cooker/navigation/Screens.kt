package com.gkgio.borsch_cooker.navigation

import android.content.Context
import com.gkgio.borsch_cooker.auth.InputPhoneFragment
import com.gkgio.borsch_cooker.auth.ValidatePhoneFragment
import com.gkgio.borsch_cooker.main.MainFragment
import com.gkgio.borsch_cooker.onboarding.OnboardingFragment
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

    object InputPhoneFragmentScreen : SupportAppScreen() {
        override fun getFragment() = InputPhoneFragment()
    }

    class ValidatePhoneFragmentScreen(private val phone: String) : SupportAppScreen() {
        override fun getFragment() = ValidatePhoneFragment.newInstance(phone)
    }
}