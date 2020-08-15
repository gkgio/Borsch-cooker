package com.gkgio.borsch_cooker.meals

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MealsPagerAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private companion object {
        private const val TABS_COUNT = 2
    }

    override fun getItem(position: Int) = when(position) {
        0 -> MealsListFragment.newInstance(MealsConstants.MEALS_TYPE_SINGLES)
        1 -> MealsListFragment.newInstance(MealsConstants.MEALS_TYPE_LUNCHES)
        else -> MealsListFragment.newInstance(MealsConstants.MEALS_TYPE_SINGLES)
    }

    override fun getCount(): Int = TABS_COUNT

}