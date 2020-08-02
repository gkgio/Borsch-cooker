package com.gkgio.borsch_cooker.main

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.meals.MealsFragment
import com.gkgio.borsch_cooker.orders.OrdersFragment
import com.gkgio.borsch_cooker.own.OwnFragment
import com.gkgio.borsch_cooker.support.SupportFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(), BottomBarTabsSwitcher {

    private companion object {
        private const val PAGE_CACHE_SIZE = 4
        private const val PAGE_OWN = 0
        private const val PAGE_MEALS = 1
        private const val PAGE_ORDERS = 2
        private const val PAGE_SUPPORT = 3
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mainViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { view, insets ->
            view.isVisible = insets.systemWindowInsetBottom == 0
            insets
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openFirstTab.observeValue(this) {
            childFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.fragmentContainer,
                    getFragment(R.id.tab_own),
                    getFragmentTag(R.id.tab_own)
                )
                .commit()
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            val tag = getFragmentTag(it.itemId)
            val fragment = childFragmentManager.findFragmentByTag(tag) ?: getFragment(it.itemId)
            replaceFragment(fragment, tag)
            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    private fun getFragment(itemId: Int) = when (itemId) {
        R.id.tab_own -> OwnFragment()
        R.id.tab_meals -> MealsFragment()
        R.id.tab_orders -> OrdersFragment()
        R.id.tab_support -> SupportFragment()
        else -> throw IllegalArgumentException("Unsupported tab")
    }

    private fun getFragmentTag(itemId: Int) = when (itemId) {
        R.id.tab_own -> PAGE_OWN.toString()
        R.id.tab_meals -> PAGE_MEALS.toString()
        R.id.tab_orders -> PAGE_ORDERS.toString()
        R.id.tab_support -> PAGE_SUPPORT.toString()
        else -> throw IllegalArgumentException("Unsupported tab")
    }

    override fun switchToOwnTab() {
        bottomNavigation.selectedItemId = R.id.tab_own
    }

    override fun switchToMealsTab() {
        bottomNavigation.selectedItemId = R.id.tab_meals
    }

    override fun switchToOrdersTab() {
        bottomNavigation.selectedItemId = R.id.tab_orders
    }

    override fun switchToSupportTab() {
        bottomNavigation.selectedItemId = R.id.tab_support
    }
}

interface BottomBarTabsSwitcher {
    fun switchToOwnTab()
    fun switchToMealsTab()
    fun switchToOrdersTab()
    fun switchToSupportTab()
}