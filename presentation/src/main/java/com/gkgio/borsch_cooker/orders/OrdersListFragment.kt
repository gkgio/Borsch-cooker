package com.gkgio.borsch_cooker.orders

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
import kotlinx.android.synthetic.main.fragment_orders_list.*
import kotlinx.android.synthetic.main.layout_no_information.*

class OrdersListFragment : BaseFragment<OrdersListViewModel>() {

    private lateinit var ordersAdapter: OrdersListAdapter

    companion object {
        fun newInstance(ordersType: String) = OrdersListFragment().apply {
            this.ordersType = ordersType
        }
    }

    var ordersType: String by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.fragment_orders_list

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ordersListViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(ordersType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrdersAdapter()
        viewModel.state.observeValue(this) {
            ordersAdapter.setOrdersList(it.ordersList)
            ordersNoInformationError.isVisible = it.ordersList.isEmpty()
            errorTextView.text = getString(R.string.orders_no_information)
        }
    }

    private fun initOrdersAdapter() {
        ordersAdapter = OrdersListAdapter {
            viewModel.clickOrder(it)
        }
        ordersListRv.adapter = ordersAdapter
        ordersListRv.layoutManager = LinearLayoutManager(context)
    }
}