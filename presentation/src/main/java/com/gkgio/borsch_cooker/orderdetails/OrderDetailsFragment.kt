package com.gkgio.borsch_cooker.orderdetails

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.orders.OrdersMealsAdapter
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_order_details.*
import kotlinx.android.synthetic.main.toolbar_two_icon_view.view.*

class OrderDetailsFragment : BaseFragment<OrderDetailsViewModel>() {

    companion object {
        fun newInstance(orderId: String) = OrderDetailsFragment().apply {
            this.orderId = orderId
        }
    }

    private var orderId: String by FragmentArgumentDelegate()
    private lateinit var orderMealsAdapter: OrdersMealsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_order_details

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.orderDetailsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(orderId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        viewModel.state.observeValue(this) {
            orderMealsAdapter.setMealsList(it.orderDetails.meals)
            orderDetailsSum.text = getString(R.string.orders_sum, 400) //TODO
            orderDetailsDelivery.text = "Самовывоз" //TODO
        }
        toolbar.setLeftIconClickListener {
            viewModel.clickLeftIcon()
        }
        toolbar.titleTextView.text = getString(R.string.orders_number, orderId)
    }

    private fun initMealsRv() {
        orderMealsAdapter = OrdersMealsAdapter(listOf(), true) {}
        orderMealsListRv.adapter = orderMealsAdapter
        orderMealsListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}