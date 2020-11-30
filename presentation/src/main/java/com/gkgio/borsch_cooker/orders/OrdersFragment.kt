package com.gkgio.borsch_cooker.orders

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : BaseFragment<OrdersViewModel>() {

    private lateinit var typeTitlesAdapter: OrdersTypeTitlesAdapter
    private lateinit var pagerAdapter: OrdersPagerAdapter

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ordersViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTypeTitlesRv()
        initViewPager()
        viewModel.titlesLiveData.observeValue(viewLifecycleOwner) {
            typeTitlesAdapter.setTitlesRes(it)
        }

        ordersSrl.setColorSchemeColors(resources.getColor(R.color.blue_text))
        ordersSrl.setOnRefreshListener {
            viewModel.onSwipedToRefresh()
        }

        viewModel.cancelSwipe.observeValue(this) {
            ordersSrl.isRefreshing = false
        }
    }

    private fun initViewPager() {
        pagerAdapter = OrdersPagerAdapter(
            childFragmentManager
        )
        ordersTypeViewPager.adapter = pagerAdapter
        ordersTypeViewPager.currentItem = 0

        ordersTypeViewPager.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                typeTitlesAdapter.markAsSelected(position)
                ordersTypeTitlesRv.smoothScrollToPosition(position)
                viewModel.onCurrentPagePositionChanged(position)
            }
        })
    }

    private fun initTypeTitlesRv() {
        typeTitlesAdapter = OrdersTypeTitlesAdapter {
            ordersTypeViewPager.setCurrentItem(it, false)
        }
        ordersTypeTitlesRv.adapter = typeTitlesAdapter
        ordersTypeTitlesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}