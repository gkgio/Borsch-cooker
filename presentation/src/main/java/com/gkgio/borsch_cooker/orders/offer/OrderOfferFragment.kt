package com.gkgio.borsch_cooker.orders.offer

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_order_offer.*

class OrderOfferFragment : BaseFragment<OrderOfferViewModel>() {

    private lateinit var offerAdapter: OrderOfferAdapter

    companion object {

        const val TIMER_ACTIVE = 300000L
        val TAG = OrderOfferFragment::class.java.simpleName
        fun newInstance(order: OrdersListItemUi) = OrderOfferFragment().apply {
            this.order = order
        }
    }

    private var order: OrdersListItemUi by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.fragment_order_offer

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.orderOfferViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(order)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderOfferTimerPb.max = TIMER_ACTIVE.toInt()
        initOfferRv()

        viewModel.orderInfo.observeValue(this) {
            acceptPriceTv.text = getString(R.string.order_offer_accept_button, it.price)
            offerAdapter.setOrderInfo(it.offerOrder)
        }

        viewModel.activeTime.observeValue(this) {
            orderOfferTimerPb.progress = it.activeTimer
            offerTimeTv.text = it.activeTime
        }

        viewModel.isLoading.observeValue(this) { loadingPb.isVisible = it }

        acceptButtonFl.setDebounceOnClickListener {
            viewModel.onAcceptClick()
        }

        declineButton.setDebounceOnClickListener {
            viewModel.onDeclineClick()
        }
    }

    private fun initOfferRv() {
        offerAdapter = OrderOfferAdapter()
        offerRv.adapter = offerAdapter
        offerRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}