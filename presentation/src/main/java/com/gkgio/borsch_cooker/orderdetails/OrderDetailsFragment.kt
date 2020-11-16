package com.gkgio.borsch_cooker.orderdetails

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.borsch_cooker.orders.OrdersMealsAdapter
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_order_details.*

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
            orderMealsAdapter.setMealsList(it.orderDetails?.meals)
            orderDetailsSum.text = getString(R.string.orders_sum, it.orderDetails?.price.toString())
            orderDetailsDelivery.text =
                if (it.orderDetails?.type == OrdersConstants.ORDERS_TAKE_DELIVERY)
                    getString(R.string.order_delivery) else getString(R.string.order_pickup)
        }

        viewModel.status.observeValue(this) {
            if (it) {
                showError("Статус успешно обновлен!") //TODO
            } else {
                showNetworkError(getString(R.string.server_error))
            }
        }

        toolbar.setLeftIconClickListener {
            viewModel.clickLeftIcon()
        }

        toolbar.setTitle(getString(R.string.orders_number, orderId))

        clientChatView.setDebounceOnClickListener {
            viewModel.onClientChatClicked()
        }

        readyForPickupButton.setDebounceOnClickListener {
            viewModel.onReadyToPickupClicked()
        }
    }

    private fun initMealsRv() {
        orderMealsAdapter = OrdersMealsAdapter(true)
        orderMealsListRv.adapter = orderMealsAdapter
        orderMealsListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}