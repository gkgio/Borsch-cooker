package com.gkgio.borsch_cooker.meals.addlunch

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_lunches_select_meals.*

class SelectMealsFragment : BaseFragment<SelectMealsViewModel>() {

    private lateinit var mealsAdapter: SelectMealsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_lunches_select_meals

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.selectMealsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        toolbar.setTitle("Выбор блюд") //TODO
        toolbar.setLeftIconClickListener {
            onBackClick()
        }
        viewModel.state.observeValue(this) {
            progressBar.isVisible = it.isLoading
            mealsAdapter.setMealsList(it.mealsList)
            if (it.isInitialError) {
                showError(getString(R.string.default_error))
            }
        }
        viewModel.checkedMeals.observeValue(this) {
            saveSelectedMealsTv.text =
                resources.getQuantityString(R.plurals.meals_checked_counter, it.size, it.size)
        }
        saveSelectedMealsButton.setDebounceOnClickListener {
            onBackClick()
        }
    }

    private fun initMealsRv() {
        mealsAdapter = SelectMealsAdapter { id: String, checked: Boolean ->
            viewModel.onChangeCheckedMeals(id, checked)
        }
        selectMealsRv.adapter = mealsAdapter
        selectMealsRv.layoutManager = LinearLayoutManager(requireContext())
    }

}