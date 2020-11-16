package com.gkgio.borsch_cooker.orders.offer.sheet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.model.MealModel
import kotlinx.android.synthetic.main.layout_meals_info_sheet.*

class MealsSheet(private val meals: MealModel) : BaseBottomSheetDialog() {

    private lateinit var viewModel: MealsSheetViewModel
    private lateinit var mealsAdapter: MealsSheetAdapter

    override fun getLayoutId(): Int = R.layout.layout_meals_info_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.mealsSheetViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOfferMealsRv()
        dismiss.setDebounceOnClickListener {
            dismiss()
        }
    }

    private fun initOfferMealsRv() {
        mealsAdapter = MealsSheetAdapter()
        offerMealsRv.adapter = mealsAdapter
        offerMealsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mealsAdapter.setMealsList(meals.meals)
    }
}