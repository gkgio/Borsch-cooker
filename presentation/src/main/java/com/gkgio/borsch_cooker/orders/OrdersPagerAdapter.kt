package com.gkgio.borsch_cooker.orders

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class OrdersPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private companion object {
        private const val TABS_COUNT = 2
    }

    override fun getItem(position: Int) = when (position) {
        0 -> OrdersListFragment.newInstance(OrdersConstants.ORDERS_TYPE_ACTIVE)
        1 -> OrdersListFragment.newInstance(OrdersConstants.ORDERS_TYPE_ALL)
        else -> OrdersListFragment.newInstance(OrdersConstants.ORDERS_TYPE_ACTIVE)
    }

    override fun getCount() = TABS_COUNT
}