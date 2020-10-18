package com.gkgio.borsch_cooker.own.activemeals

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import kotlinx.android.synthetic.main.fragment_active_meals.*

class ActiveMealsFragment : BaseFragment<ActiveMealsViewModel>() {

    private lateinit var activeMealsAdapter: ActiveMealsAdapter

    companion object {
        const val ACTION_PLUS = 0
        const val ACTION_MINUS = 1
        const val ACTION_INFO = 2
        const val ACTION_MEAL_STATE = 3
    }

    override fun getLayoutId(): Int = R.layout.fragment_active_meals

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.activeMealsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        toolbar.setTitle(getString(R.string.own_active_meals))
        viewModel.state.observeValue(this) {
            activeMealsAdapter.setActiveMeals(it.activeMeals)
            if (it.isInitialError) showError(getString(R.string.error_empty_text))
        }
        toolbar.setLeftIconClickListener {
            onBackClick()
        }
    }

    private fun initMealsRv() {
        activeMealsAdapter = ActiveMealsAdapter { id, action, isActive ->
            viewModel.onItemClick(id, action, isActive)
        }
        activeMealsRv.adapter = activeMealsAdapter
        activeMealsRv.layoutManager = LinearLayoutManager(requireContext())
    }

}