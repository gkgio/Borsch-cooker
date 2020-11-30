package com.gkgio.borsch_cooker.orderdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.layout_order_details_status_sheet.*

class OrderDetailsStatusSheet : BaseBottomSheetDialog() {

    companion object {
        fun newInstance(orderId: String, status: String) = OrderDetailsStatusSheet().apply {
            this.status = status
            this.orderId = orderId
        }
    }

    private lateinit var viewModel: OrderDetailsStatusViewModel
    private lateinit var orderDetailsStatusAdapter: OrderDetailsStatusAdapter
    private lateinit var orderStatusChangeListener: OrderStatusChangeListener
    private var status: String by FragmentArgumentDelegate()
    private var orderId: String by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.layout_order_details_status_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.orderDetailsStatusViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrderDetailsStatusRv()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderStatusChangeListener = when {
            parentFragment is OrderStatusChangeListener -> parentFragment as OrderStatusChangeListener
            context is OrderStatusChangeListener -> context
            else -> throw IllegalStateException("Either parentFragment or context must implement OrderStatusChangeListener")
        }
    }

    private fun initOrderDetailsStatusRv() {
        orderDetailsStatusAdapter = OrderDetailsStatusAdapter(status) {
            orderStatusChangeListener.onOrderStatusChange(it)
        }
        orderDetailsStatusRv.adapter = orderDetailsStatusAdapter
        orderDetailsStatusRv.layoutManager = LinearLayoutManager(requireContext())
    }

    internal interface OrderStatusChangeListener {
        fun onOrderStatusChange(status: String)
    }

}