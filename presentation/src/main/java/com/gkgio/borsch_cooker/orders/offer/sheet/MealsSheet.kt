package com.gkgio.borsch_cooker.orders.offer.sheet

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel

class MealsSheet : BaseBottomSheetDialog() {

    private lateinit var viewModel: MealsSheetViewModel

    override fun getLayoutId(): Int = R.layout.layout_meals_info_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.mealsSheetViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}