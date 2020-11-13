package com.gkgio.borsch_cooker.orders.offer

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.sheet.DeliverySheet
import com.gkgio.borsch_cooker.orders.offer.sheet.MealsSheet
import kotlinx.android.synthetic.main.fragment_order_offer.*

class OrderOfferFragment : BaseFragment<OrderOfferViewModel>() {

    private lateinit var offerAdapter: OrderOfferAdapter

    companion object {

        const val TIMER_ACTIVE = 300000L
        const val HOLDER_DELIVERY = "holderDelivery"
        const val HOLDER_MEALS = "holderMeals"
        val TAG = OrderOfferFragment::class.java.simpleName

        fun newInstance(order: String) = OrderOfferFragment().apply {
            this.order = order
        }
    }

    private var order: String? = null

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
        viewModel.state.observeValue(this) {
            offerAdapter.setOrderInfo(it.offerOrder)
        }
        viewModel.activeTime.observeValue(this) {
            orderOfferTimerPb.progress = it.activeTimer
            offerTimeTv.text = it.activeTime
        }
        viewModel.sheetClick.observeValue(this) {
            when (it.type) {
                HOLDER_DELIVERY -> showDialog(DeliverySheet(), TAG)
                HOLDER_MEALS -> showDialog(MealsSheet(), TAG)
            }
        }
        acceptButtonFl.setDebounceOnClickListener {
            viewModel.onAcceptClick()
        }
        declineButton.setDebounceOnClickListener {
            viewModel.onDeclineClick()
        }
    }

    private fun initOfferRv() {
        offerAdapter = OrderOfferAdapter {
            viewModel.onSheetClick(it)
        }
        offerRv.adapter = offerAdapter
        offerRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

}