package com.gkgio.borsch_cooker.meals.addmeal

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.layout_meal_type_sheet.*

class MealSelectTypeSheet : BaseBottomSheetDialog() {

    private lateinit var viewModel: MealSelectTypeViewModel

    override fun getLayoutId(): Int = R.layout.layout_meal_type_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.mealSelectTypeViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lunchIsAvailable.observeValue(this) {
            lunchWarnTv.isVisible = !it
            lunchAddTv.isVisible = it
        }
        lunchAddTv.setDebounceOnClickListener {
            viewModel.addLunchClick()
            dismiss()
        }
        mealAddTv.setDebounceOnClickListener {
            viewModel.addMealClick()
            dismiss()
        }
    }

}