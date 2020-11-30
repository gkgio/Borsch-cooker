package com.gkgio.borsch_cooker.orders.offer.some

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.layout_some_order_offers_sheet.*

class SomeOrderOffersSheet : BaseBottomSheetDialog() {

    companion object {
        fun newInstance(orders: List<OrdersListItemUi>) = SomeOrderOffersSheet().apply {
            this.orders = orders
        }

        const val BUTTON_ACCEPT = "buttonAccept"
        const val BUTTON_DECLINE = "buttonDecline"
    }

    private var orders: List<OrdersListItemUi> by FragmentArgumentDelegate()
    private lateinit var orderOffersAdapter: SomeOrderOffersAdapter
    private lateinit var viewModel: SomeOrderOffersViewModel

    override fun getLayoutId(): Int = R.layout.layout_some_order_offers_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.someOrderOffersViewModel }
        viewModel.init(orders)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStopCatchOffers()

        initOrderOffersAdapter()

        viewModel.orders.observeValue(this) {
            orderOffersAdapter.setOrdersList(it)
        }

        viewModel.isLoading.observeValue(this) {
            loadingOffersPb.isVisible = it
        }

        viewModel.closeDialog.observeValue(this) {
            dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStartCatchOffers()
    }

    private fun initOrderOffersAdapter() {
        orderOffersAdapter = SomeOrderOffersAdapter { orderId: String, action: String ->
            viewModel.onOrderActionClicked(orderId, action)
        }

        someOrderOffersRv.adapter = orderOffersAdapter
        someOrderOffersRv.layoutManager = LinearLayoutManager(requireContext())
    }

}