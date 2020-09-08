package com.gkgio.borsch_cooker.meals

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.orders.OrdersTypeTitlesAdapter
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.fragment_meals.*

class MealsFragment : BaseFragment<MealsViewModel>() {

    private lateinit var pagerAdapter: MealsPagerAdapter
    private lateinit var typeTitlesAdapter: OrdersTypeTitlesAdapter

    override fun getLayoutId(): Int = R.layout.fragment_meals

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mealsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTypeTitlesRv()
        initViewPager()
        viewModel.titlesLiveData.observeValue(viewLifecycleOwner) {
            typeTitlesAdapter.setTitlesRes(it)
        }
        mealsAddMealButton.setDebounceOnClickListener {
            viewModel.addMealClick()
        }
    }

    private fun initViewPager() {
        pagerAdapter = MealsPagerAdapter(childFragmentManager)
        mealsTypeViewPager.adapter = pagerAdapter
        mealsTypeViewPager.currentItem = 0
        mealsTypeViewPager.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                typeTitlesAdapter.markAsSelected(position)
                mealsTypeTitlesRv.smoothScrollToPosition(position)
                viewModel.onCurrentPagePositionChanged(position)
            }
        })
    }

    private fun initTypeTitlesRv() {
        typeTitlesAdapter = OrdersTypeTitlesAdapter {
            mealsTypeViewPager.setCurrentItem(it, false)
        }
        mealsTypeTitlesRv.adapter = typeTitlesAdapter
        mealsTypeTitlesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}