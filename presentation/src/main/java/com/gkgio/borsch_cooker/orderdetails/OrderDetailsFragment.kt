package com.gkgio.borsch_cooker.orderdetails

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.getQuantityText
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.borsch_cooker.orders.OrdersMealsAdapter
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_order_details.*

class OrderDetailsFragment : BaseFragment<OrderDetailsViewModel>(), OrderDetailsStatusSheet.OrderStatusChangeListener {

    companion object {
        val TAG = OrderDetailsFragment::class.java.simpleName
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
            orderDetailsMessagesTv.text = requireContext().getQuantityText(
                    R.plurals.order_messages,
                    it.orderDetails?.unreadMessagesCount ?: 0
            )
            orderDetailsDelivery.text =
                    if (it.orderDetails?.type == OrdersConstants.ORDERS_TAKE_DELIVERY)
                        getString(R.string.order_delivery) else getString(R.string.order_pickup)
            changeStatusButton.text = when (it.orderDetails?.status) {
                OrdersConstants.ORDERS_STATUS_COOKING -> getString(R.string.orders_status_cooking)
                OrdersConstants.ORDERS_STATUS_CAN_PICKUP -> getString(R.string.orders_status_can_pickup)
                OrdersConstants.ORDERS_STATUS_DELIVERING -> getString(R.string.orders_status_delivering)
                OrdersConstants.ORDERS_STATUS_CANCELED -> getString(R.string.orders_status_canceled)
                OrdersConstants.ORDERS_STATUS_ACCEPTED -> getString(R.string.orders_status_accepted)
                else -> getString(R.string.orders_status_change)
            }
        }

        viewModel.status.observeValue(this) {
            showDialog(OrderDetailsStatusSheet.newInstance(orderId, it), TAG)
        }

        toolbar.setLeftIconClickListener {
            viewModel.clickLeftIcon()
        }

        clientChatView.setDebounceOnClickListener {
            viewModel.onClientChatClicked()
        }

        changeStatusButton.setDebounceOnClickListener {
            viewModel.onChangeStatusClicked()
        }

        orderDetailsNumTv.text = getString(R.string.orders_number, orderId)
    }

    private fun initMealsRv() {
        orderMealsAdapter = OrdersMealsAdapter(true)
        orderMealsListRv.adapter = orderMealsAdapter
        orderMealsListRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onOrderStatusChange(status: String) {
        viewModel.onStatusChanged(status)
    }

}