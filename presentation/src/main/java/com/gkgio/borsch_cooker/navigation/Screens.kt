package com.gkgio.borsch_cooker.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.gkgio.borsch_cooker.auth.InputPhoneFragment
import com.gkgio.borsch_cooker.auth.ValidatePhoneFragment
import com.gkgio.borsch_cooker.main.MainFragment
import com.gkgio.borsch_cooker.meals.addlunch.AddLunchFragment
import com.gkgio.borsch_cooker.meals.addlunch.SelectMealsFragment
import com.gkgio.borsch_cooker.meals.addmeal.AddMealFragment
import com.gkgio.borsch_cooker.onboarding.OnboardingFragment
import com.gkgio.borsch_cooker.profile.avatar.InputAvatarFragment
import com.gkgio.borsch_cooker.profile.name.InputNameFragment
import com.gkgio.borsch_cooker.orderdetails.OrderDetailsFragment
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.chat.OrderChatFragment
import com.gkgio.borsch_cooker.own.activemeals.ActiveMealsFragment
import com.gkgio.borsch_cooker.orders.offer.OrderOfferFragment
import com.gkgio.borsch_cooker.profile.ProfileFragment
import com.gkgio.borsch_cooker.profile.address.FindAddressFragment
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
        private val orderId: String
    ) : SupportAppScreen() {
        override fun getFragment() = OrderDetailsFragment.newInstance(orderId)
    }

    class AddMealScreen() : SupportAppScreen() {
        override fun getFragment() = AddMealFragment()
    }

    class AddLunchScreen() : SupportAppScreen() {
        override fun getFragment() = AddLunchFragment()
    }

    class SelectMealsScreen() : SupportAppScreen() {
        override fun getFragment() = SelectMealsFragment()
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

    object ActiveMealsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = ActiveMealsFragment()
    }

    class InputPhoneFragmentScreen(
        private val isFromOnboarding: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() = InputPhoneFragment.newInstance(isFromOnboarding)
    }

    class ValidatePhoneFragmentScreen(
        private val tmpToken: String,
        private val phone: String,
        private val isFromOnboarding: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() =
            ValidatePhoneFragment.newInstance(tmpToken, phone, isFromOnboarding)
    }

    object InputNameFragmentScreen : SupportAppScreen() {
        override fun getFragment() = InputNameFragment()
    }

    object InputAvatarFragmentScreen : SupportAppScreen() {
        override fun getFragment() = InputAvatarFragment()
    }

    object FindAddressFragmentScreen : SupportAppScreen() {
        override fun getFragment() = FindAddressFragment()
    }

    object ProfileFragmentScreen : SupportAppScreen() {
        override fun getFragment() = ProfileFragment()
    }

    class OrderOfferFragmentScreen(private val order: String) : SupportAppScreen() {
        override fun getFragment() = OrderOfferFragment.newInstance(order) }

    class OrderChatFragmentScreen(
        private val orderId: String,
        private val cookerId: String
    ) : SupportAppScreen() {
        override fun getFragment() = OrderChatFragment.newInstance(
            orderId = orderId,
            userId = cookerId
        )
    }
    
}