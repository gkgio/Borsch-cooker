package com.gkgio.borsch_cooker.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gkgio.borsch_cooker.meals.MealsFragment
import com.gkgio.borsch_cooker.own.OwnFragment
import com.gkgio.borsch_cooker.support.SupportFragment

class MainPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OwnFragment()
            1 -> MealsFragment()
            2 -> SupportFragment()
            else -> throw IllegalArgumentException("Unsupported tab")
        }
    }

    override fun getCount(): Int = PAGE_COUNT
}