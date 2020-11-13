package com.gkgio.borsch_cooker.orders.offer.sheet

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.holder.DeliveryHolder
import kotlinx.android.synthetic.main.layout_delivery_info_sheet.*

class DeliverySheet(private val info: DeliveryHolder) : BaseBottomSheetDialog() {

    private lateinit var viewModel: DeliverySheetViewModel

    override fun getLayoutId(): Int = R.layout.layout_delivery_info_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.deliverySheetViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dismiss.setDebounceOnClickListener {
            dismiss()
        }
    }

}