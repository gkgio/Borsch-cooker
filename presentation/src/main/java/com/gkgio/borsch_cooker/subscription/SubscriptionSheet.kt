package com.gkgio.borsch_cooker.subscription

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel

class SubscriptionSheet : BaseBottomSheetDialog() {

    private lateinit var viewModel: SubscriptionViewModel

    override fun getLayoutId(): Int = R.layout.layout_subscription_sheet

    override fun setupView(view: View) {
        super.setupView(view)
        viewModel = createViewModel { AppInjector.appComponent.subscriptionViewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO
    }

}