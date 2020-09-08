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
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_meals_list.*
import kotlinx.android.synthetic.main.layout_no_information.*

class MealsListFragment : BaseFragment<MealsListViewModel>() {

    private lateinit var mealsAdapter: MealsAdapter
    var mealsType: String by FragmentArgumentDelegate()

    companion object {
        fun newInstance(mealsType: String) = MealsListFragment().apply {
            this.mealsType = mealsType
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_meals_list

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mealsListViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(mealsType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        viewModel.state.observeValue(this) {
            mealsAdapter.setMealsList(it.mealsList)
            mealsNoInformationError.isVisible = it.mealsList.isEmpty()
            noInformationButton.isVisible = it.mealsList.isEmpty()
            errorTextView.text = getString(R.string.meals_no_information)
        }
    }

    private fun initMealsRv() {
        mealsAdapter = MealsAdapter(MealsConstants.MEALS_TYPE_LUNCHES == mealsType, {
            //TODO editClick
        }, {
            //TODO lunchClick
        })
        mealsListRv.adapter = mealsAdapter
        mealsListRv.layoutManager = LinearLayoutManager(requireContext())
    }
}