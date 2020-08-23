package com.gkgio.borsch_cooker.meals

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.fragment_meals.*
import kotlinx.android.synthetic.main.layout_no_information.*

class MealsFragment : BaseFragment<MealsViewModel>() {

    private lateinit var mealsAdapter: MealsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_meals

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mealsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        viewModel.state.observeValue(this) {
            mealsAdapter.setMealsList(it.mealsList)
            mealsNoInformationError.isVisible = it.mealsList.isEmpty()
            mealsAdd.isVisible = it.mealsList.isEmpty()
            errorTextView.text = getString(R.string.meals_no_information)
        }
        mealsAddMealButton.setDebounceOnClickListener {
            viewModel.addMealClick()
        }
    }

    private fun initMealsRv() {
        mealsAdapter = MealsAdapter {
            //TODO go to editPage
        }
        mealsRv.adapter = mealsAdapter
        mealsRv.layoutManager = LinearLayoutManager(requireContext())
    }
}