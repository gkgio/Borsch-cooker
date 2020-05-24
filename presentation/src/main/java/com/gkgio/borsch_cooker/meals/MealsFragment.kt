package com.gkgio.borsch_cooker.meals

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel

class MealsFragment : BaseFragment<MealsViewModel>() {


    override fun getLayoutId(): Int = R.layout.fragment_meals

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mealsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}